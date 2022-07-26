package com.example.kinesis.config

import com.amazonaws.auth.STSAssumeRoleSessionCredentialsProvider
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class KinesisConsumerConfig(
    private val awsProperties: AwsProperties,
) {
    private val workerId = "merchant-worker"

    @Bean
    fun KinesisClientLibConfiguration(): KinesisClientLibConfiguration {
        return KinesisClientLibConfiguration(
            awsProperties.sessionName,
            awsProperties.streamName,
            STSAssumeRoleSessionCredentialsProvider.Builder(awsProperties.roleArn, awsProperties.sessionName)
                .build(),
            workerId
        ).withRegionName(awsProperties.region)
            .withInitialLeaseTableReadCapacity(100)
    }
}