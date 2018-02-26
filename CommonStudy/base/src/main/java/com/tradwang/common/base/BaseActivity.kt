package com.tradwang.common.base

import android.widget.Toast
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

/**
 *  Project Name : CommonStudy
 *  Package Name : com.tradwang.common.base
 *  @since 2018/2/11 13: 28
 *  @author : TradWang
 *  @email : trad_wang@sina.com
 *  @version :
 *  @describe :
 */
open class BaseActivity : RxAppCompatActivity() {

    fun showToast(msg: String?) {
        msg?.let {
            Toast.makeText(this@BaseActivity, msg, Toast.LENGTH_SHORT).show()
        }
    }
}