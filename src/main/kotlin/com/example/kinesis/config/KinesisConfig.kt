package com.example.kinesis.config

import com.amazonaws.auth.STSAssumeRoleSessionCredentialsProvider
import com.amazonaws.services.kinesis.AmazonKinesis
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KinesisConfig(
    private val awsProperties: AwsProperties
) {
    @Bean
    fun amazonKinesis(): AmazonKinesis {

        return AmazonKinesisClientBuilder.standard()
            .withRegion(awsProperties.region)
            .withCredentials(
                STSAssumeRoleSessionCredentialsProvider.Builder(awsProperties.roleArn, awsProperties.sessionName)
                    .build()
            )
            .build()
    }
}