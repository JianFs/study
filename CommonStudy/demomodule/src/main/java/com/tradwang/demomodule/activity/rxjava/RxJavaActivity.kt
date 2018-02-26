package com.tradwang.demomodule.activity.rxjava

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tradwang.demomodule.R
import kotlinx.android.synthetic.main.demo_activity_rx_java.*

class RxJavaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_activity_rx_java)

        btn_create_operator.setOnClickListener { startActivity(Intent(this@RxJavaActivity, RxCreateActivity::class.java)) }

        btn_transform_operator.setOnClickListener { startActivity(Intent(this@RxJavaActivity, RxTransformActivity::class.java)) }

        btn_merge_operator.setOnClickListener { startActivity(Intent(this@RxJavaActivity, RxMergeActivity::class.java)) }

    }
}
