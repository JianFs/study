package com.tradwang.demomodule.net

import com.google.gson.JsonObject
import com.tradwang.centre.base.BaseEntity
import com.tradwang.common.base.ApiFactory
import com.tradwang.common.base.App
import io.reactivex.Flowable
import io.rx_cache2.*
import io.rx_cache2.internal.RxCache
import io.victoralbertos.jolyglot.GsonSpeaker
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import java.util.concurrent.TimeUnit

/**
 *  Project Name : bianla_android
 *  Package Name : com.tradwang.sample.retrofit
 *  @since 2017/12/11 10: 23
 *  @author : TradWang
 *  @email : trad_wang@sina.com
 *  @version :
 */
object DemoApi {

    private val cache: CacheApi = RxCache.Builder().persistence(App.getInstance().externalCacheDir, GsonSpeaker()).using(CacheApi::class.java)

    /**
     * 用RxCache包装RxJava
     */
    fun getAndCacheData(isUpdate: Boolean): Flowable<BaseEntity<JsonObject>> {
        return cache.cacheGetData(NetApi.Factory.getApi().getData(), EvictProvider(isUpdate))
    }

    /**
     *@param cacheKey 常用与不同用户数据缓存
     */
    fun getAndCacheData(isUpdate: Boolean, cacheKey: String): Flowable<BaseEntity<JsonObject>> {
        return cache.cacheGetData(NetApi.Factory.getApi().getData(), EvictDynamicKey(isUpdate), DynamicKey(cacheKey))
    }

    /**
     *@param cacheKey 不同用户
     * @param cacheGroup 分页缓存
     */
    fun getAndCacheData(isUpdate: Boolean, cacheKey: String, cacheGroup: String): Flowable<BaseEntity<JsonObject>> {
        return cache.cacheGetData(NetApi.Factory.getApi().getData(), EvictDynamicKeyGroup(isUpdate), DynamicKeyGroup(cacheKey, cacheGroup))
    }

    @EncryptKey("bianla") //数据加解密的密钥
    interface CacheApi {
        /**
         * @param flowable 要缓存的flowable
         * @param evictProvider 缓存设置  EvictProvider(true)  无论过期时间是否到了都 重新缓存数据    EvictProvider(false) 不重新缓存数据 时间到了才重新缓存  缓存命名规则 方法名+$d$d$d$$g$g$g$
         *  EvictProvider 继承关系 EvictDynamicKeyGroup --> EvictDynamicKey -->  EvictProvider
         *  EvictDynamicKeyGroup  参数为true 删除对应分页下的数据 一般与 DynamicKeyGroup 成对出现
         *  EvictDynamicKey   参数为true 删除对应组下的数据 一般与 DynamicKey成对出现
         *  EvictProvider  参数为true 删除当前方法下的数据
         * LifeCache 缓存策略 60秒缓存
         */
        @Encrypt //使用数据加解密
        @LifeCache(duration = 60, timeUnit = TimeUnit.SECONDS)  //60秒缓存
        fun cacheGetData(flowable: Flowable<BaseEntity<JsonObject>>, evictProvider: EvictProvider): Flowable<BaseEntity<JsonObject>>

        /**
         *@param flowable 要缓存的 Rx发射源
         * @param evictDynamicKey 缓存设置 true 无论过期时间是否到了都重新 @param dynamicKey 下的数据 false 时间到了才会重新缓存
         * @param dynamicKey  当前方法下跟据不同的 key 缓存不同的文件 缓存命名规则 方法名+$d$d$d$+dynamicKey+$g$g$g$
         */
        @Encrypt //使用数据加解密
        @LifeCache(duration = 60, timeUnit = TimeUnit.SECONDS)  //60秒缓存
        fun cacheGetData(flowable: Flowable<BaseEntity<JsonObject>>, evictDynamicKey: EvictDynamicKey, dynamicKey: DynamicKey): Flowable<BaseEntity<JsonObject>>

        /**
         *@param flowable 要缓存的 Rx发射源
         * @param evictDynamicKeyGroup 缓存设置 true 无论过期时间是否到了都重新 @param dynamicKeyGroup 下的数据 false 时间到了才会重新缓存
         * @param dynamicKeyGroup  当前方法下跟据不同的 key - value  缓存不同的文件 缓存命名规则 方法名+$d$d$d$+dynamicKey+$g$g$g$+dynamicGroup
         */
        @Encrypt //使用数据加解密
        @LifeCache(duration = 60, timeUnit = TimeUnit.SECONDS)  //60秒缓存
        fun cacheGetData(flowable: Flowable<BaseEntity<JsonObject>>, evictDynamicKeyGroup: EvictDynamicKeyGroup, dynamicKeyGroup: DynamicKeyGroup): Flowable<BaseEntity<JsonObject>>
    }

    interface NetApi {
        /**
         *get请求
         *GET 注解标识要请求的URL和请求类型
         * @return Flowable<BaseEntity<JsonObject>> RxJava事件发射源 和要解析的数据bean
         */
        @GET("URL")
        fun getData(): Flowable<BaseEntity<JsonObject>>

        /**
         *post请求
         * POST注解标识URL和请求类型
         * Multipart 标识是表单请求
         * @param  list 表单请求请求列表
         * @return RxJava事件发射源 和要解析的数据bean
         */
        @POST("URL")
        @Multipart
        fun postMultipart(@Part list: MutableList<MultipartBody.Part>): Flowable<BaseEntity<JsonObject>>

        /**
         *post 请求体  可以postJson   -->  ApiFactory.getJsonReqBody(jsonString)
         * @param requestBody 请求体
         * @return RxJava事件发射源 和要解析的数据bean
         */
        @POST("URL")
        fun postJson(@Body requestBody: RequestBody): Flowable<BaseEntity<JsonObject>>

        class Factory private constructor() {
            companion object {
                fun getApi(): NetApi = ApiFactory.getRetrofit().create(NetApi::class.java)
            }
        }
    }
}