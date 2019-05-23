package com.yf.dialog.lib.core.lifecycle

import android.content.Context
import com.yf.dialog.lib.core.IDialog

/**
 * 用于监听全局dialog的生命周期回调
 * 回调执行时机在单独注册的show或dismiss的监听之前
 */
interface DialogLifecycleCallbacks {
    fun onDialogShow(dialog: IDialog?, context: Context?, token: String?)
    fun onDialogDismiss(dialog: IDialog?,context: Context?, token: String?)
    fun onDialogCancel(dialog: IDialog?,context: Context?, token: String?)
}