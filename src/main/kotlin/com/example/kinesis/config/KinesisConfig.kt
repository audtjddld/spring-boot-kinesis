package com.example.kinesis.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.http.apache.ApacheHttpClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient
import software.amazon.awssdk.services.sts.StsClient
import software.amazon.awssdk.services.sts.auth.StsAssumeRoleCredentialsProvider
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest
import software.amazon.kinesis.common.KinesisClientUtil

@Configuration
class KinesisConfig(
    private val awsProperties: AwsProperties,

    ) {
    private val region = Region.of(awsProperties.region)

    @Bean
    fun stsClient(): StsClient {
        return StsClient.builder()
            .region(region)
            .httpClient(ApacheHttpClient.create())
            .build()
    }

    @Bean
    fun amazonAsyncKinesisClient(): KinesisAsyncClient {
        return KinesisClientUtil.createKinesisAsyncClient(
            KinesisAsyncClient.builder()
                .region(region)
                .credentialsProvider(
                    StsAssumeRoleCredentialsProvider.builder()
                        .stsClient(stsClient())
                        .asyncCredentialUpdateEnabled(true)
                        .refreshRequest(
                            AssumeRoleRequest.builder()
                                .roleArn(awsProperties.roleArn)
                                .roleSessionName(awsProperties.sessionName)
                                .build()
                        )
                        .build()
                )
        )

        /*
        return AmazonKinesisClientBuilder.standard()
            .withRegion(awsProperties.region)
            .withCredentials(
                STSAssumeRoleSessionCredentialsProvider
                    .Builder(awsProperties.roleArn, awsProperties.sessionName)
                    .build()
            )
            .build()

         */
    }
}