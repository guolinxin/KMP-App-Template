package com.jetbrains.kmpapp.data.kucoin

import kotlinx.serialization.Serializable

@Serializable
data class KucoinTicker(
    val sequence: Long,
    val bestAsk: String,
    val size: String,
    val price: String,
    val bestBidSize: String,
    val bestBid: String,
    val bestAskSize: String,
    val time: Long,
)
