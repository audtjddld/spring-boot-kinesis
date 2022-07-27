package com.example.kinesis.web.model

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

data class StockTrade(
    val tickerSymbol: String,
    val price: Double,
    val quantity: Long,
    val id: Long
) {
    companion object {
        private val objectMapper = jacksonObjectMapper()

        fun fromJsonAsBytes(bytes: ByteArray): StockTrade {
            return objectMapper.readValue(bytes)
        }
    }

    fun toJsonAsBytes(): ByteArray {
        return objectMapper.writeValueAsBytes(this)
    }

}
