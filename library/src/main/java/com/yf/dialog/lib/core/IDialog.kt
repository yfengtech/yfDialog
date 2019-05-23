package com.yf.dialog.lib.core

/**
 * Created by yf on 2019/4/20.
 */
interface IDialog {
    fun show()
    fun dismiss()
    fun isShowing(): Boolean
}