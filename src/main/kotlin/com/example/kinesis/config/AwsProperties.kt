package com.example.kinesis.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("aws.sts")
data class AwsProperties(
    val region: String,
    val roleArn: String,
    val sessionName: String,
    val streamName:String,
)