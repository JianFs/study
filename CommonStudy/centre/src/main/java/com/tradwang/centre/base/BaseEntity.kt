package com.tradwang.centre.base

import java.io.Serializable

/**
 * Project Name : CommonStudy
 * Package Name : com.tradwang.centre.base
 *
 * @author : TradWang
 * @version :
 * @email : trad_wang@sina.com
 * @describe :
 * @since 2018/2/26 14: 12
 */
class BaseEntity<T> : Serializable {

    /**
     * 是否成功
     */
    var success: Boolean = false
    /**
     * 错误码表
     */
    var code: Int = -2
    /**
     * 用于客户端弹窗提示
     */
    var alertMsg: String? = null
    /**
     * 数据体
     */
    var data: T? = null
}
