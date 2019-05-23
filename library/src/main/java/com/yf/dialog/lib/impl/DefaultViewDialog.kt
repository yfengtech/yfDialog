package com.yf.dialog.lib.impl

/**
 * Created by yf on 2019/4/20.
 */
class DefaultViewDialog : SingleBtnDialog() {

    override fun getButtonText(): String = "default button"
    override fun getTitle(): String = "default title"
    override fun getMessage(): String = "default message"
}