package org.mikehenry.kotlin_playground.configuration

import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.utility.DockerImageName
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import java.time.Duration
import java.time.temporal.ChronoUnit

@Configuration
class S3ConfigurationToTest {

    val log = KotlinLogging.logger {}

    init {
        localstack.start()
    }

    @Profile("prod")
    @Bean
    fun s3Client(): S3Client = awsS3Client()

    @Profile("prod")
    @Bean
    fun s3Presigner(): S3Presigner = awsS3Presigner()

    companion object {
        @Container
        private val localstack = LocalStackContainer(DockerImageName.parse("localstack/localstack:0.11.3"))
            .withServices(LocalStackContainer.Service.S3)
            .withEnv("DEFAULT_REGION", "us-east-1")
            .withEnv("DEBUG", "1")
            .withEnv("TESTCONTAINERS_RYUK_DISABLED", "true")
            .withReuse(true)
            .withStartupTimeout(Duration.of(2, ChronoUnit.MINUTES))
    }

    fun awsS3Client(): S3Client {
        log.info { "Starting LocalStack container on ${localstack.getEndpointOverride(LocalStackContainer.Service.S3)}" }
        return S3Client.builder()
            .endpointOverride(localstack.getEndpointOverride(LocalStackContainer.Service.S3))
            .credentialsProvider(
                StaticCredentialsProvider.create(AwsBasicCredentials.create(localstack.accessKey, localstack.secretKey))
            )
            .region(Region.of(localstack.region))
            .build()
    }

    fun awsS3Presigner(): S3Presigner {
        log.info { "Starting LocalStack container on ${localstack.getEndpointOverride(LocalStackContainer.Service.S3)}" }
        return S3Presigner.builder()
            .endpointOverride(localstack.getEndpointOverride(LocalStackContainer.Service.S3))
            .credentialsProvider(
                StaticCredentialsProvider.create(AwsBasicCredentials.create(localstack.accessKey, localstack.secretKey))
            )
            .region(Region.of(localstack.region))
            .build()
    }
}