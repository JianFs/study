package com.tradwang.common.base

import android.util.Log
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *  Project Name : CommonStudy
 *  Package Name : com.tradwang.common.base
 *  @since 2018/2/11 17: 53
 *  @author : TradWang
 *  @email : trad_wang@sina.com
 *  @version :
 *  @describe :
 */
object ApiFactory {

    /**
     *获取Api
     * @return ApiFactory  通讯
     */
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://www.baidu.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())
                .build()
    }

    /**
     *获取Json请求体
     * @param json Json字符串
     * @return RequestBody
     */
    fun getJsonReqBody(json: String): RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

    /**
     * 获取OkHttpClient
     */
    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Log.i("HttpLog", message) }).setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
    }
}