package com.example.kinesis.consumer

import mu.KotlinLogging
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Component
import software.amazon.kinesis.coordinator.Scheduler
import javax.annotation.PostConstruct

@Component
class StockTradeConsumer(
    private val scheduler: Scheduler,
    private val threadPoolTaskExecutor: ThreadPoolTaskExecutor
) {
    private val logger = KotlinLogging.logger {}

    @PostConstruct
    fun init() {
        logger.info { "kinesis consumer running." }
        threadPoolTaskExecutor.execute {
            scheduler.run()
        }
    }
}
