package com.example.kinesis.consumer

import com.example.kinesis.web.model.StockTrade
import mu.KotlinLogging
import software.amazon.kinesis.lifecycle.events.*
import software.amazon.kinesis.processor.ShardRecordProcessor
import java.nio.charset.StandardCharsets

class StockTradeRecordProcessor : ShardRecordProcessor {
    private val logger = KotlinLogging.logger {}
    private val decoder = StandardCharsets.UTF_8

    override fun initialize(initializationInput: InitializationInput) {
        val kinesisShardId = initializationInput.shardId()
        logger.info { "initializing record processor for shard : $kinesisShardId" }
    }

    override fun processRecords(processRecordsInput: ProcessRecordsInput) {

        logger.info("Processing ${processRecordsInput.records().size} record(s)")

        processRecordsInput.records().forEach {
            logger.info { "partition Key : ${it.partitionKey()} , sequence : ${it.sequenceNumber()}" }
            val decode = decoder.decode(it.data())
            val message = StockTrade.fromJsonAsBytes(decode.toString().toByteArray())
            logger.info { "message : $message" }

        }
    }

    override fun leaseLost(leaseLostInput: LeaseLostInput?) {
        TODO("Not yet implemented")
    }

    override fun shardEnded(shardEndedInput: ShardEndedInput) {
        logger.info { "Reached shard end checking point." }
    }

    override fun shutdownRequested(shutdownRequestedInput: ShutdownRequestedInput) {
        logger.info("Scheduler is shutting down, checkpointing.")
        shutdownRequestedInput.checkpointer().checkpoint()
    }

}
