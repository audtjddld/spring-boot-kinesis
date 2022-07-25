package com.example.kinesis.web.model

data class StockTrade(
    val tickerSymbol: String,
    val TradeType: TradeType,
    val price: Double,
    val quantity: Long,
    val id: Long
)

enum class TradeType {
    BUY, SELL
}