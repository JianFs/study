package com.tradwang.demomodule.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.JsonObject
import com.tradwang.centre.base.BaseEntity
import com.tradwang.demomodule.R
import com.tradwang.demomodule.net.DemoApi
import io.reactivex.FlowableSubscriber
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscription

class RetrofitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_activity_retrofit)

        DemoApi.getAndCacheData(false).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(object : FlowableSubscriber<BaseEntity<JsonObject>> {
            override fun onComplete() {
                println("onComplete")
            }

            override fun onSubscribe(s: Subscription) {
                s.request(Long.MAX_VALUE)
            }

            override fun onNext(t: BaseEntity<JsonObject>?) {
                println("$t ----------------")
            }

            override fun onError(t: Throwable?) {
                println("${t?.message}----------------")
            }
        })
    }
}
