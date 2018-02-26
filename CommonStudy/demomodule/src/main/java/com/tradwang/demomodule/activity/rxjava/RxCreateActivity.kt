package com.tradwang.demomodule.activity.rxjava

import android.os.Bundle
import com.tradwang.common.base.BaseActivity
import com.tradwang.demomodule.R

import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class RxCreateActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_activity_rx_create)

        createRx()

    }

    /**
     * 创建操作符
     */
    fun createRx() {
        //1
        Observable.create(ObservableOnSubscribe<String> { emitter ->

            emitter.onNext("我是 onNext 发送的数据")
            emitter.onComplete()
        }).subscribe(object : Observer<String> {
            override fun onComplete() {
                showToast("onComplete")
            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(t: String) {
                showToast(t)
            }

            override fun onError(e: Throwable) {
                showToast(e.message)
            }

        })
        //2
        Observable.create<String> { it.onNext("我是 onNext 发送的数据") }.subscribe { showToast(it) }

        //3  在创建后就会发送这些对象，相当于执行了onNext(1)、onNext(2)、onNext(3)、onNext(4)
        Observable.just(1, 2, 3, 4).subscribe { showToast(it.toString()) }

        //4 发送一个集合
        val strings = arrayListOf("1", "2", "3", "4", "5")
        Observable.fromArray(strings).subscribe { showToast(it.toString()) }

        //遍历发送集合
        Observable.fromIterable(strings).subscribe { showToast(it) }

        //延迟发送一个Long数字 一般用于测试
        Observable.timer(3, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe { showToast(it.toString()) }
        //延迟3秒  一秒一次发送 从零开始的递增Long数
        Observable.interval(3, 1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe { showToast(it.toString()) }
    }
}
