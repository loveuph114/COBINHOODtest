package dev.reece.cobinhood.data

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Created by reececheng on 2019/1/5.
 */
interface ApiService {
    companion object {
        fun create(): ApiService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.cobinhood.com")
                    .build()

            return retrofit.create(ApiService::class.java)
        }
    }


    @GET("/v1/market/tickers")
    fun getTickers(): Observable<Model.TickerObject>

}