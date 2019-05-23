package com.yf.dialog

import android.app.Application
import android.content.Context
import android.util.Log
import com.yf.dialog.lib.core.IDialog
import com.yf.dialog.lib.core.YFDialogManager
import com.yf.dialog.lib.core.lifecycle.DialogLifecycleCallbacks


class MyApplication : Application() {

    private val mDialogLifecycleCallbacks = object : DialogLifecycleCallbacks {
        override fun onDialogShow(dialog: IDialog?, context: Context?, token: String?) {
            debugLog("DialogLifecycleCallbacks onDialogShow$token dialog:${dialog?.toString()}")
        }

        override fun onDialogDismiss(dialog: IDialog?, context: Context?, token: String?) {
            debugLog("DialogLifecycleCallbacks onDialogDismiss$token dialog:${dialog?.toString()}")
        }

        override fun onDialogCancel(dialog: IDialog?, context: Context?, token: String?) {
            debugLog("DialogLifecycleCallbacks onDialogCancel$token dialog:${dialog?.toString()}")
        }
    }

    override fun onCreate() {
        super.onCreate()
        YFDialogManager.registerLifeCycle(mDialogLifecycleCallbacks)
    }
}

val DEBUG = true
internal inline fun debugLog(value: String) {
    if (DEBUG) {
        Log.d("yf_dialog_app", value)
    }
}