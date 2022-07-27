package com.example.kinesis.context.listener

import com.example.kinesis.consumer.StockTradeConsumer
import mu.KotlinLogging
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class KinesisConsumerExecutor(
    private val stockTradeConsumer: StockTradeConsumer,
) {
    private val logger = KotlinLogging.logger {}

    @EventListener
    fun ready(contextReadyEvent: ApplicationReadyEvent) {
        logger.info { "Application Ready and stock trace consumer start!" }
        stockTradeConsumer.run()
    }
}
