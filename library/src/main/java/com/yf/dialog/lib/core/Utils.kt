package com.yf.dialog.lib.core

import android.content.res.Resources
import android.util.Log

/**
 * Module内部使用的一些工具类，不提供给外部调用
 * Created by yf on 2019/4/21.
 */
internal object Utils {
    /**
     * dp转px
     *
     * @param dpValue dp值
     * @return 转化后的px
     */
    fun dp2px(dpValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}

val DEBUG = true
internal inline fun debugLog(value: String) {
    if (DEBUG) {
        Log.d("yf_dialog", value)
    }
}