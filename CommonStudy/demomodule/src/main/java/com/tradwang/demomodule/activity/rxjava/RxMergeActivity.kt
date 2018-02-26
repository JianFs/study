package com.tradwang.demomodule.activity.rxjava

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tradwang.demomodule.R
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer

class RxMergeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_activity_rx_mrege)


        //concat() / concatArray()  组合多个被观察者一起发送数据，合并后 按发送顺序串行执行
        //二者区别：组合被观察者的数量，即concat（）组合被观察者数量≤4个，而concatArray（）则可＞4个
        Observable.concat(Observable.just(1), Observable.just(2, 3, 4), Observable.just(100, 300, 500)).subscribe { println(it.toString()) }

        //merge()/ mergeArray() 组合多个被观察者一起发送数据，合并后 按时间线并行执行
        //二者区别：组合被观察者的数量，即merge()组合被观察者数量≤4个，而mergeArray()则可＞4个
        //区别上述concat()操作符：同样是组合多个被观察者一起发送数据，但concat()操作符合并后是按发送顺序串行执行
        Observable.merge(Observable.just(1, 2, 3), Observable.just(4, 5, 6), Observable.just(100, 300, 500)).subscribe { println(it.toString()) }

        //concatDelayError() / mergeDelayError() 解决concat和 merge 时 其中一个发送error时整个队列停止的问题 次操作符会等到所有事件发送完成时才抛出error

        //zip() 该类型的操作符主要是对多个被观察者中的事件进行合并处理
        //合并 多个被观察者（Observable）发送的事件，生成一个新的事件序列（即组合过后的事件序列），并最终发送

        val observable1 = Observable.just("A", "B", "C", "D")
        val observable2 = Observable.just(1, 2, 3)

        Observable.zip(observable1, observable2, BiFunction<String, Int, String> { a, b ->
            "$a  $b"
        }).subscribe { println(it) }

        //collect() 将被观察者Observable发送的数据事件收集到一个数据结构里
        Observable.just(1, 2, 3, 4, 5).collect({ mutableListOf<Int>() }, { t1, t2 -> t1.add(t2) }).subscribe(Consumer { println(it) })
    }
}
