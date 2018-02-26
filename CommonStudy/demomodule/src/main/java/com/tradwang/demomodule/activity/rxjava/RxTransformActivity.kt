package com.tradwang.demomodule.activity.rxjava

import android.os.Bundle
import com.tradwang.common.base.BaseActivity
import com.tradwang.demomodule.R
import io.reactivex.Observable

class RxTransformActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_activity_rx_transform)
        rxTransform()
    }

    private fun rxTransform() {
        //转换元素
        Observable.just(1, 2, 3).map { "$it  哎哎哎" }.subscribe { showToast(it.toString()) }
        //转换队列
        Observable.just(1, 2, 3, 4).flatMap({ Observable.just("$it" + "哈哈") }).subscribe { println(it) }
        // 设置缓存区大小 & 步长
        // 缓存区大小->每次从被观察者中获取的事件数量
        //步长->每次获取新事件的数量
        Observable.just(1, 2, 3, 4, 5, 6).buffer(3, 2).subscribe { it.forEach { println(it) } }
    }
}
