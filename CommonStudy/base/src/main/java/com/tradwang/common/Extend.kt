package com.tradwang.common

import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.concurrent.TimeUnit

/**
 *  Package Name : com.tradwang.common.base
 *  @since 2017/12/28 18: 36
 *  @author : TradWang
 *  @email : trad_wang@sina.com
 *  @version : 存放扩展函数
 */

/**
 * Float扩展 四舍五入方法
 * @param round 保留小数点后几位
 */
fun Float.round(round: Int): String {
    return BigDecimal(this.toDouble()).setScale(round, RoundingMode.HALF_UP).toFloat().toString()
}

/**
 * Double扩展 四舍五入方法
 * @param round 保留小数点后几位
 */
fun Double.round(round: Int): String {
    return BigDecimal(this).setScale(round, RoundingMode.HALF_UP).toDouble().toString()
}

/**
 * Int扩展 四舍五入方法
 * @param round 保留小数点后几位
 */
fun Int.round(round: Int): String {
    return BigDecimal(this).setScale(round, RoundingMode.HALF_UP).toInt().toString()
}

/**
 * String? 转Float  为空时返回0f
 */
fun String?.toFloatNotNull(): Float {
    return this?.toFloatOrNull() ?: 0f
}

/**
 * String? 转Int  为空时返回0
 */
fun String?.toIntNotNull(): Int {
    return this?.toIntOrNull() ?: 0
}

/**
 * String? 转Float  为空时返回0.0
 */
fun String?.toDoubleNotNull(): Double {
    return this?.toDoubleOrNull() ?: 0.0
}

/**
 * String? 转Float  为空时返回0L
 */
fun String?.toLongNotNull(): Long {
    return this?.toLongOrNull() ?: 0L
}

/**
 * 防止重复点击
 * @param delaySeconds 重复点击间隔
 * @param listener 监听
 */
fun View.setDelayClickListener(delaySeconds: Long?, listener: ((View) -> Unit)?) {
    RxView.clicks(this).throttleFirst(delaySeconds ?: 0, TimeUnit.SECONDS).subscribeOn(AndroidSchedulers.mainThread()).subscribe { listener?.invoke(this) }
}

/**
 * 防止重复点击
 * @param delaySeconds 重复点击间隔
 */
fun View.setRxDelayClick(delaySeconds: Long?): Observable<Any>? {
    return RxView.clicks(this).throttleFirst(delaySeconds ?: 0, TimeUnit.SECONDS).subscribeOn(AndroidSchedulers.mainThread())
}