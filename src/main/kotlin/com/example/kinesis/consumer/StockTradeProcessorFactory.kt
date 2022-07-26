package com.example.kinesis.consumer

import software.amazon.kinesis.processor.ShardRecordProcessor
import software.amazon.kinesis.processor.ShardRecordProcessorFactory


class StockTradeProcessorFactory : ShardRecordProcessorFactory {
    override fun shardRecordProcessor(): ShardRecordProcessor {
        return StockTradeRecordProcessor()
    }
}
