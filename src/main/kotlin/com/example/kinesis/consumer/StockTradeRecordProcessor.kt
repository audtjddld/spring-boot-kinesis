package com.example.kinesis.consumer

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessor
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorCheckpointer
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.ShutdownReason
import com.amazonaws.services.kinesis.model.Record
import com.example.kinesis.web.model.StockTrade
import mu.KotlinLogging

class StockTradeRecordProcessor : IRecordProcessor {

    private val logger = KotlinLogging.logger {}

    override fun initialize(shardId: String?) {
        println("ready for consuming message!! shardId:$shardId")
    }

    override fun processRecords(records: MutableList<Record>?, checkpointer: IRecordProcessorCheckpointer?) {
        if (records == null) {
            return
        }
        records.stream().forEach {

            logger.info { "consuming!!! $it checkpointer: $checkpointer" }

            val stockTrade = StockTrade.fromJsonAsBytes(it.data.array())

            logger.info { "got $stockTrade" }

        }
    }

    override fun shutdown(checkpointer: IRecordProcessorCheckpointer?, reason: ShutdownReason?) {
        println("shutdown...!!! reason : $reason")
    }
}
