package com.yf.dialog.lib.impl

import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.yf.dialog.lib.core.IDialog
import com.yf.dialog.lib.core.view.AbsDefaultDialog
import com.yf.dialog.library.R

/**
 * 默认提供的两个按钮dialog
 *
 * @author yf
 * @date 2019/4/20
 */
abstract class DoubleBtnDialog : AbsDefaultDialog() {

    override fun onViewCreated(view: View, dialog: IDialog) {
        super.onViewCreated(view, dialog)
        view.findViewById<LinearLayout>(R.id.llDoubleButtonLayout).visibility = View.VISIBLE

        val leftButton = view.findViewById<Button>(R.id.leftBtn)
        leftButton.visibility = View.VISIBLE
        leftButton.text = getLeftButtonText()
        leftButton.setOnClickListener { onLeftClickListener(dialog) }

        view.findViewById<View>(R.id.viewButtonDivider).visibility = View.VISIBLE

        val rightButton = view.findViewById<Button>(R.id.rightBtn)
        rightButton.visibility = View.VISIBLE
        rightButton.text = getRightButtonText()
        rightButton.setOnClickListener { onRightClickListener(dialog) }
    }

    abstract fun getLeftButtonText(): String
    abstract fun getRightButtonText(): String
    open fun onLeftClickListener(dialog: IDialog) {
        dialog.dismiss()
    }

    open fun onRightClickListener(dialog: IDialog) {
        dialog.dismiss()
    }
}