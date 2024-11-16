package app.newtrend.trade.data.kucoin

import com.jetbrains.kmpapp.data.kucoin.KucoinTicker
import com.jetbrains.kmpapp.data.model.RequestState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface KucoinApi {
    suspend fun getTicker(symbol: String): RequestState<KucoinTicker>
}

class KucoinMarketApi : KucoinApi {

    companion object {
        const val TICKER_URI = "/api/v1/market/orderbook/level1"
    }

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 15000
        }
        install(DefaultRequest) {
            headers {
                append("Accept", "application/json")
            }
        }
    }

    override suspend fun getTicker(symbol: String): RequestState<KucoinTicker> {
        val uri = "$TICKER_URI?symbol=$symbol"
        return try {
            val response = httpClient.get(uri)
            if (response.status.value == 200) {
                RequestState.Success(data = response.body())
            } else {
                RequestState.Error(message = response.status.description)
            }
        } catch (e: Exception) {
            RequestState.Error(message = e.message.toString())
        }
    }
}