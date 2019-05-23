package com.yf.dialog.lib.core

import android.content.Context
import android.util.Log
import com.yf.dialog.library.R

/**
 * 检测弹出样式是否配置
 */
internal inline fun Context.checkDialogThemeByCustom(style: Int) {
    val a = obtainStyledAttributes(intArrayOf(R.attr.dialogStyle))
    if (!a.hasValue(0)) {
        // 没有定义style，应用默认样式
        Log.w("DialogCompat", "You have no configuration dialog style with this activity!")
        theme.applyStyle(R.style.DefaultDialogStyle, true)
    }
    // 有指定样式，覆盖默认样式
    if (style > 0) {
        theme.applyStyle(style, true)
    }
    a.recycle()
}
