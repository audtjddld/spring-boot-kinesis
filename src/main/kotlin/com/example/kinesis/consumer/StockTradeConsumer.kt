package com.example.kinesis.consumer

import mu.KotlinLogging
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Component
import software.amazon.kinesis.coordinator.Scheduler

@Component
class StockTradeConsumer(
    private val scheduler: Scheduler,
    private val threadPoolTaskExecutor: ThreadPoolTaskExecutor
) {
    private val logger = KotlinLogging.logger {}

    fun run() {
        logger.info { "kinesis consumer running." }
        threadPoolTaskExecutor.execute {
            scheduler.run()
        }
    }
}
