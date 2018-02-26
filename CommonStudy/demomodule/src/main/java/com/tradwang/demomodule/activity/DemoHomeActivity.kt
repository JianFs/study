package com.tradwang.demomodule.activity


import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.jakewharton.rxbinding2.view.RxView
import com.tradwang.centre.DEMO_HOME_ACTIVITY
import com.tradwang.common.base.BaseActivity
import com.tradwang.demomodule.R
import com.tradwang.demomodule.activity.rxjava.RxJavaActivity
import kotlinx.android.synthetic.main.demo_activity_main.*

@Route(path = DEMO_HOME_ACTIVITY)
class DemoHomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_activity_main)
        RxView.clicks(btnRxPermissions).subscribe { startActivity(Intent(this@DemoHomeActivity, RxPermissionActivity::class.java)) }
        RxView.clicks(btnRxJava).subscribe { startActivity(Intent(this@DemoHomeActivity, RxJavaActivity::class.java)) }
        RxView.clicks(btnPinYinSearch).subscribe { startActivity(Intent(this@DemoHomeActivity, PinYinSearchActivity::class.java)) }
        RxView.clicks(btnCrash).subscribe { startActivity(Intent(this@DemoHomeActivity, CrashTestActivity::class.java)) }
        RxView.clicks(btnRetrofit).subscribe { startActivity(Intent(this@DemoHomeActivity,RetrofitActivity::class.java)) }
    }
}
