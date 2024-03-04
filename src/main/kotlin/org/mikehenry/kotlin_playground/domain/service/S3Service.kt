package org.mikehenry.kotlin_playground.domain.service

import mu.KotlinLogging
import org.mikehenry.kotlin_playground.domain.exception.UploadFailedProblem
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.exception.SdkException
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.io.IOException
import java.io.InputStream

class S3Service(
    private val s3Client: S3Client
) {

    private val log = KotlinLogging.logger {}

    fun uploadFile(fileIdentifier: String, file: MultipartFile, bucketName: String) {
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
}