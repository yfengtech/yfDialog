package com.yf.dialog.lib.core

import android.content.Context
import com.yf.dialog.lib.core.lifecycle.DialogLifecycleCallbacks
import java.util.ArrayList

/**
 * YFDialog的管理类，用于管理全局的dialog生命周期等...
 *
 * @author yf
 * @date 2019/4/30
 */
object YFDialogManager {

    private val mDialogLifecycleCallbacks = ArrayList<DialogLifecycleCallbacks>()

    /**
     * 注册dialog生命周期回调
     */
    @JvmStatic
    fun registerLifeCycle(callback: DialogLifecycleCallbacks) {
        synchronized(mDialogLifecycleCallbacks) {
            mDialogLifecycleCallbacks.add(callback)
        }
    }

    /**
     * 取消注册dialog生命周期回调
     */
    @JvmStatic
    fun unregisterLifeCycle(callback: DialogLifecycleCallbacks) {
        synchronized(mDialogLifecycleCallbacks) {
            mDialogLifecycleCallbacks.remove(callback)
        }
    }

    internal fun dispatchDialogShow(dialog:IDialog?,context: Context?, token: String?) {
        mDialogLifecycleCallbacks.forEach {
            it.onDialogShow(dialog,context, token)
        }
    }

    internal fun dispatchDialogDismiss(dialog:IDialog?,context: Context?, token: String?) {
        mDialogLifecycleCallbacks.forEach {
            it.onDialogDismiss(dialog,context, token)
        }
    }

    internal fun dispatchDialogCancel(dialog:IDialog?,context: Context?, token: String?) {
        mDialogLifecycleCallbacks.forEach {
            it.onDialogCancel(dialog,context, token)
        }
    }
}