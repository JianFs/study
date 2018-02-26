package com.tradwang.demomodule.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxbinding2.view.RxView
import com.tradwang.common.seachutils.SearchUtils
import com.tradwang.demomodule.R
import kotlinx.android.synthetic.main.demo_activity_pin_yin_search_avtivity.*
import java.lang.StringBuilder

class PinYinSearchActivity : AppCompatActivity() {

    private val strings = arrayListOf("哈啊哈 ", "傻嘎", " 手动阀", " 手动阀 ", "发射", " 点发", "生了就", "  发 色打", " 发士大", " 夫", "  破 ", "请问u", " 日期 ", "日 期了", " 咖啡 ", "店 ", "发 ", "的算 ", "法的", " 脾气 ", "就 ", "安分")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_activity_pin_yin_search_avtivity)
        RxView.clicks(btnSearch).map { tetKeyText.text.toString().trim() }.map { str -> SearchUtils.search(str, strings) }.map { strings ->
            val string = StringBuilder()
            strings.forEach { string.append(it).append("    ") }
            return@map string
        }.subscribe { tv_result.text = it.toString() }
    }
}
