package com.example.kinesis

import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker
import com.example.kinesis.consumer.StockTradeProcessorFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import java.util.concurrent.CompletableFuture

@ConfigurationPropertiesScan
@SpringBootApplication
class KinesisApplication

fun main(args: Array<String>) {
    runApplication<KinesisApplication>(*args)
}



