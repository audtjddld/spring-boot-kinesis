package com.example.kinesis.config

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StsConfig {

    /*
    @Bean
    fun stsClient():StsClient {
        return stsClient.builder()
            .region(region)
            .credentialsProvider(ProfileCredentialsProvider.create())
            .build();
    }

     */
}