package com.example.kinesis.config

import com.amazonaws.auth.STSAssumeRoleSessionCredentialsProvider
import com.amazonaws.services.kinesis.producer.KinesisProducer
import com.amazonaws.services.kinesis.producer.KinesisProducerConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KinesisProducerConfig(
    private val awsProperties: AwsProperties,
) {

    @Bean
    fun kinesisProducer(): KinesisProducer {
        val configuration = KinesisProducerConfiguration()
            .setVerifyCertificate(false)
            .setRegion(awsProperties.region)
            .setCredentialsProvider(
                STSAssumeRoleSessionCredentialsProvider.Builder(awsProperties.roleArn, awsProperties.sessionName)
                    .build()
            ).setRequestTimeout(60000)
            .setMaxConnections(2)

        return KinesisProducer(configuration)
    }
}
