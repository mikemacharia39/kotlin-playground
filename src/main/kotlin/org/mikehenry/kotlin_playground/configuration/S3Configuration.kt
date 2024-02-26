package org.mikehenry.kotlin_playground.configuration

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.presigner.S3Presigner

@Configuration
@ConditionalOnWebApplication
class S3Configuration {

    @Profile("!prod")
    @Bean
    fun s3Client(): S3Client {
        return S3Client.builder()
            .credentialsProvider(DefaultCredentialsProvider.builder().build())
            .build()
    }

    @Bean
    fun s3Presigner(): S3Presigner {
        return S3Presigner.builder()
            .credentialsProvider(DefaultCredentialsProvider.builder().build())
            .build()
    }
}