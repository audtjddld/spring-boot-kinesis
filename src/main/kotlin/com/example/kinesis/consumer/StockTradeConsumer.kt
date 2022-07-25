package com.example.kinesis.consumer

import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture
import javax.annotation.PostConstruct

@Component
class StockTradeConsumer(
    private val kinesisClientLibConfiguration: KinesisClientLibConfiguration
) {

    @PostConstruct
    fun init() {
        val worker = Worker.Builder()
            .recordProcessorFactory(StockTradeProcessorFactory())
            .config(kinesisClientLibConfiguration)
            .build()

        CompletableFuture.runAsync(worker)
    }
}