package com.example.kinesis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class KinesisApplication

fun main(args: Array<String>) {
    runApplication<KinesisApplication>(*args)
}



