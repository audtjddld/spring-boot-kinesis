package com.example.kinesis.web

import com.amazonaws.services.kinesis.producer.KinesisProducer
import com.example.kinesis.web.model.StockTrade
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.nio.ByteBuffer
import kotlin.random.Random

@RestController
class ProducingController(
    private val producer: KinesisProducer
) {

    var count: Int = 0
    val streamName = "StockTradeStream"
    private val logger = KotlinLogging.logger {}

    @GetMapping()
    fun produceMessage(): ResponseEntity<Void> {

        val message = StockTrade(
            "AMZN",
            Random.nextDouble(),
            Random.nextLong(),
            Random.nextLong()
        )

        logger.info { "send message : $message" }
        producer.addUserRecord(
            streamName,
            "AMZN",
            ByteBuffer.wrap(message.toJsonAsBytes())
        )

        return ResponseEntity.ok().build()
    }
}
