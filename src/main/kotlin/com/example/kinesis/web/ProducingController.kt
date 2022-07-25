package com.example.kinesis.web

import com.amazonaws.services.kinesis.AmazonKinesis
import com.amazonaws.services.kinesis.model.PutRecordRequest
import com.amazonaws.services.kinesis.producer.KinesisProducer
import com.example.kinesis.web.model.StockTrade
import com.example.kinesis.web.model.TradeType
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.nio.ByteBuffer
import kotlin.random.Random

@RestController
class ProducingController(
    private val producer: KinesisProducer
) {

    var count:Int = 0
    val streamName = "StockTradeStream"

    @GetMapping()
    fun produceMessage(): ResponseEntity<Void> {

        val message = StockTrade(
            "AMZN",
            tradeType(),
            Random.nextDouble(),
            Random.nextLong(),
            Random.nextLong()
        )

        producer.addUserRecord(
            streamName,
            "AMZN",
            ByteBuffer.wrap(message.toString().toByteArray())
        )

        return ResponseEntity.ok().build()
    }

    fun tradeType(): TradeType {
        return if (Random.nextInt() % 2 == 0) {
            TradeType.BUY
        } else {
            TradeType.SELL
        }
    }
}