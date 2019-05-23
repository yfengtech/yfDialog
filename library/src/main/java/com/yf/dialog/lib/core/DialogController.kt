package com.yf.dialog.lib.core

import android.content.Context
import android.content.DialogInterface
import android.support.v4.app.FragmentManager
import android.view.Gravity
import com.yf.dialog.lib.core.view.ViewWrapper

/**
 * Created by yf on 2019/4/20.
 */
@Suppress("DEPRECATION")
internal class DialogController {
    var context: Context? = null
    var viewStyle: Int? = null
    var dialogWidth = 0
    var dialogHeight = 0
    var dimAmount = 1.0f
    var gravity = Gravity.CENTER
    var animRes = 0
    var fragmentManager: FragmentManager? = null
    var viewWrapper: ViewWrapper? = null
    /**
     * 点对话框外部是否关闭
     */
    var isCanceledOnTouchOutside = true
    /**
     * 点返回键是否关闭
     */
    var isCanceledOnBack = true
    /**
     * 可选设置的dialog唯一标识，用于外部全局区分
     */
    var token: String? = null

    var dismissListener: DialogInterface.OnDismissListener? = null
    var showListener: DialogInterface.OnShowListener? = null
    var cancelListener: DialogInterface.OnCancelListener? = null

    internal class Params {
        var context: Context? = null
        var viewStyle: Int? = null
        var dialogWidth = 0
        var dialogHeight = 0
        var dimAmount = 1.0f
        var gravity = Gravity.CENTER
        var animRes = 0
        var fragmentManager: FragmentManager? = null
        var viewWrapper: ViewWrapper? = null
        /**
         * 点对话框外部是否关闭
         */
        var isCanceledOnTouchOutside = true
        /**
         * 点返回键是否关闭
         */
        var isCanceledOnBack = true
        var token: String? = null

        var dismissListener: DialogInterface.OnDismissListener? = null
        var showListener: DialogInterface.OnShowListener? = null
        var cancelListener: DialogInterface.OnCancelListener? = null

        fun apply(controller: DialogController) {
            // 参数校验
            context ?: throw IllegalArgumentException("You context is null")
            viewWrapper ?: throw IllegalArgumentException("You must add a view")

            controller.context = context
            controller.viewStyle = viewStyle
            controller.viewWrapper = viewWrapper
            controller.fragmentManager = fragmentManager
            controller.isCanceledOnTouchOutside = isCanceledOnTouchOutside
            controller.isCanceledOnBack = isCanceledOnBack
            controller.dialogWidth = dialogWidth
            controller.dialogHeight = dialogHeight
            controller.dimAmount = dimAmount
            controller.gravity = gravity
            controller.animRes = animRes
            controller.token = token
            controller.dismissListener = dismissListener
            controller.showListener = showListener
            controller.cancelListener = cancelListener
        }
    }
}