package org.mikehenry.kotlin_playground.domain.service

import mu.KotlinLogging
import org.mikehenry.kotlin_playground.domain.exception.UploadFailedProblem
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.exception.SdkException
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.CreateBucketRequest
import software.amazon.awssdk.services.s3.model.HeadBucketRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.S3Exception
import java.io.IOException
import java.io.InputStream

@Service
class S3Service(
    private val s3Client: S3Client
) {

    private val log = KotlinLogging.logger {}

    fun uploadFile(fileIdentifier: String, file: MultipartFile, bucketName: String) {
        checkIfBucketExistIfNotCreate(bucketName)
        try {
            val putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileIdentifier)
                .contentType(file.contentType)
                .contentLength(file.size)
                .build()
            val inputStream: InputStream = file.inputStream
            s3Client.putObject(
                putObjectRequest,
                RequestBody.fromInputStream(inputStream, inputStream.available().toLong())
            )
            log.info { "File $fileIdentifier uploaded successfully" }
        } catch (e: Exception) {
            when (e) {
                is SdkException, is IOException -> {
                    throw UploadFailedProblem("error.document.upload.failed", mapOf("fileIdentifier" to fileIdentifier))
                }
            }
        }
    }

    private fun checkIfBucketExistIfNotCreate(bucketName: String) {
        if (!checkIfBucketExists(bucketName)) {
            createBucket(bucketName)
        }
    }

    private fun checkIfBucketExists(bucketName: String): Boolean {
        val bucketRequest = HeadBucketRequest.builder()
            .bucket(bucketName)
            .build()
        return try {
            s3Client.headBucket(bucketRequest)
            log.info { "Bucket $bucketName exists" }
            true
        } catch (e: S3Exception) {
            log.error { "Bucket does not exist ${e.message}" }
            false
        }
    }

    private fun createBucket(bucketName: String) {
        try {
            val s3Waiter = s3Client.waiter()
            val bucketRequest = CreateBucketRequest.builder()
                .bucket(bucketName)
                .build()
            s3Client.createBucket(bucketRequest)
            val bucketRequestWaiter = HeadBucketRequest.builder()
                .bucket(bucketName)
                .build()
            s3Waiter.waitUntilBucketExists(bucketRequestWaiter)
            log.info { "Bucket $bucketName created successfully" }
        } catch (e: S3Exception) {
            log.error { "Error creating bucket: ${e.message}" }
        }
    }
}