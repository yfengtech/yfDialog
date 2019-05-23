package com.yf.dialog.lib.impl

import android.view.View
import android.widget.Button
import com.yf.dialog.lib.core.IDialog
import com.yf.dialog.lib.core.view.AbsDefaultDialog
import com.yf.dialog.library.R

/**
 * 默认提供的单个按钮dialog
 *
 * @author yf
 * @date 2019/4/20
 */
abstract class SingleBtnDialog : AbsDefaultDialog() {

    override fun onViewCreated(view: View, dialog: IDialog) {
        super.onViewCreated(view, dialog)
        val button = view.findViewById<Button>(R.id.leftBtn)
        button.visibility = View.VISIBLE
        button.text = getButtonText()
        button.setOnClickListener { onButtonClick(dialog) }
    }

    /**
     * 用来设置按钮的文本
     */
    abstract fun getButtonText(): String

    /**
     * 用来监听按钮的点击，默认实现会关闭该dialog
     */
    open fun onButtonClick(dialog: IDialog) {
        dialog.dismiss()
    }
}