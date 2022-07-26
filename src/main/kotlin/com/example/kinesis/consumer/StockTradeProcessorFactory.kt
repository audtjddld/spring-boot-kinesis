package com.example.kinesis.consumer

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessor
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory

class StockTradeProcessorFactory : IRecordProcessorFactory {

    override fun createProcessor(): IRecordProcessor {
        return StockTradeRecordProcessor()
    }
}
