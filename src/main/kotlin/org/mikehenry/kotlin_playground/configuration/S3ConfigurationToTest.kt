package org.mikehenry.kotlin_playground.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.utility.DockerImageName
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import java.time.Duration
import java.time.temporal.ChronoUnit

@Configuration
class S3ConfigurationToTest {

    @Profile("prod")
    @Bean
    fun s3Client(): S3Client = awsS3Client()

    companion object {
        private val localstack = LocalStackContainer(DockerImageName.parse("localstack/localstack:0.11.3"))
            .withServices(LocalStackContainer.Service.S3)
            .withEnv("DEFAULT_REGION", "us-east-1")
            .withEnv("HOSTNAME_EXTERNAL", "localhost")
            .withEnv("HOSTNAME", "localhost")
            .withEnv("DEBUG", "1")
            .withExposedPorts(4572)
            .withReuse(true)
            .withStartupTimeout(Duration.of(60, ChronoUnit.MINUTES))
    }

    fun awsS3Client(): S3Client = S3Client.builder()
        .endpointOverride(localstack.getEndpointOverride(LocalStackContainer.Service.S3))
        .credentialsProvider(
            StaticCredentialsProvider.create(AwsBasicCredentials.create(localstack.accessKey, localstack.secretKey))
        )
        .region(Region.of(localstack.region))
        .build()
}