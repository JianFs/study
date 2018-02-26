package com.tradwang.demomodule.activity

import android.os.Bundle
import android.util.Log
import com.jakewharton.rxbinding2.view.RxView
import com.tradwang.common.base.App
import com.tradwang.common.base.BaseActivity
import com.tradwang.common.crash.Cockroach
import com.tradwang.demomodule.R
import kotlinx.android.synthetic.main.demo_activity_crash_test.*
import kotlin.concurrent.thread

class CrashTestActivity : BaseActivity() {

    private lateinit var a: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_activity_crash_test)

        btn_install.setOnClickListener {
            Cockroach.install(object : Cockroach.ExceptionHandler {
                override fun handlerException(thread: Thread, throwable: Throwable) {
                    App.runOnUiThread(Runnable {
                        Log.e("AndroidRuntime", "--->CockroachException:$thread<---", throwable)
                        showToast("AndroidRuntime --->CockroachException: $thread<--- $throwable")
                    })
                }
            })
        }

        btn_exception_0.setOnClickListener { 1 / 0 }
        btn_exception_1.setOnClickListener { thread { throw  RuntimeException("Thread Exception") }.start() }

        btn_exception_2.setOnClickListener { a.length }

        RxView.clicks(btn_uninstall).subscribe { Cockroach.uninstall() }
    }
}
