package app.newtrend.trade.data.kucoin

import com.jetbrains.kmpapp.data.kucoin.KucoinTicker
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface KucoinApi {
    suspend fun getTicker(symbol: String): List<KucoinTicker>
}

class KucoinMarketApi(private val client: HttpClient) : KucoinApi {

    companion object {
        const val TICKER_URI = "/api/v1/market/orderbook/level1"
    }

    override suspend fun getTicker(symbol: String): List<KucoinTicker> {
        val uri = "$TICKER_URI?symbol=$symbol"
        return try {
            client.get(uri).body()
        } catch (e: Exception) {
            e.printStackTrace()

            emptyList()
        }
    }
}