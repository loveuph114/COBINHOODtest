package dev.reece.cobinhood.data

import com.google.gson.annotations.SerializedName

/**
 * Created by reececheng on 2019/1/5.
 */
object Model {
    data class Ticker(val trading_pair_id: String,
                      val timestamp: String,
                      @SerializedName("24h_high") val h_high24: Double,
                      @SerializedName("24h_low") val h_low24: Double,
                      @SerializedName("24h_open") val h_open24: Double,
                      @SerializedName("24h_volume") val h_volume24: Double,
                      val last_trade_price: Double,
                      val highest_bid: Double,
                      val lowest_ask: Double)

    data class TickerResult(val tickers: ArrayList<Ticker>)
    data class TickerObject(val success: Boolean, val result: TickerResult)
}