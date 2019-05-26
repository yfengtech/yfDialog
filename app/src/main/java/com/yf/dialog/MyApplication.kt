package com.yf.dialog

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import com.yf.dialog.lib.core.IDialog
import com.yf.dialog.lib.core.YFDialog
import com.yf.dialog.lib.core.YFDialogManager
import com.yf.dialog.lib.core.lifecycle.DialogLifecycleCallbacks
import com.yf.dialog.lib.core.view.ViewWrapper
import com.yf.dialog.lib.impl.DoubleBtnDialog
import com.yf.dialog.lib.impl.ProgressDialog
import com.yf.dialog.lib.impl.SingleBtnDialog
import com.yf.smarttemplate.SmartTemplate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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
        SmartTemplate.init(this) {
            executionItem {
                title = "测试build属性dialog"
                execute {
                    YFDialog.Builder(it)
                        .setToken("dialog number 1")
                        .setWidth(((it as Activity).windowManager.defaultDisplay.width.toFloat() * 0.3).toInt())
                        .setCanceledOnBack(false)
                        .setCanceledOnTouchOutside(false)
                        .setDimAmount(0.5f)
                        .setGravity(Gravity.CENTER)
                        .setOnCalcelListener(DialogInterface.OnCancelListener { debugLog("onCancel") })
                        .setOnShowListener(DialogInterface.OnShowListener { debugLog("onShow") })
                        .setOnDismissListener(DialogInterface.OnDismissListener { debugLog("onDismiss") })
                        .setSingleBtnView(object : SingleBtnDialog() {
                            override fun getTitle(): String = "我是个标题"
                            override fun getButtonText(): String = "知道了"
                        })
                        .build().show()
                }
            }
            executionItem {
                title = "默认dialog"
                execute {
                    YFDialog.Builder(it)
                        .setToken("dialog number 2")
                        .build().show()
                }
            }
            executionItem {
                title = "只设置title的单按钮dialog"
                execute {
                    YFDialog.Builder(it)
                        .setToken("dialog number 3")
                        .setSingleBtnView(object : SingleBtnDialog() {
                            override fun getTitle(): String = "我是个标题"
                            override fun getButtonText(): String = "知道了"
                        })
                        .build().show()
                }
            }
            executionItem {
                title = "连续弹出dialog"
                execute {
                    val dialog = YFDialog.Builder(it)
                        .setSingleBtnView(object : SingleBtnDialog() {
                            override fun getTitle(): String = "我是个标题"
                            override fun getButtonText(): String = "知道了"
                            override fun onButtonClick(dialog: IDialog) {
                                val dialog1 = YFDialog.Builder(it)
                                    .setSingleBtnView(object : SingleBtnDialog() {
                                        override fun getTitle(): String = "我是个标题"
                                        override fun getButtonText(): String = "知道了"
                                    })
                                    .build()
                                dialog1.show()

                            }
                        })
                        .build()
                    dialog.show()
                }
            }
            executionItem {
                title = "只设置message的单按钮dialog"
                execute {
                    YFDialog.Builder(it)
                        .setToken("dialog number 3")
                        .setSingleBtnView(object : SingleBtnDialog() {
                            override fun getMessage(): String = "我是个标题"
                            override fun getButtonText(): String = "知道了"
                        })
                        .build().show()
                }
            }
            executionItem {
                title = "只有一个按钮的单行字并且自定义样式dialog"
                execute {
                    YFDialog.Builder(it)
                        .setViewStyle(R.style.MyCustomDialogStyle)
                        .setSingleBtnView(object : SingleBtnDialog() {
                            override fun getTitle(): String = "我是个标题"
                            override fun getButtonText(): String = "知道了"
                        })
                        .build().show()
                }
            }
            executionItem {
                title = "只有一个按钮的双行字dialog"
                execute {
                    YFDialog.Builder(it)
                        .setSingleBtnView(object : SingleBtnDialog() {
                            override fun getTitle(): String = "我是个标题"
                            override fun getMessage(): String = "我是个内容"
                            override fun getButtonText(): String = "知道了"
                            override fun onButtonClick(dialog: IDialog) {
                                dialog.dismiss()
                            }
                        })
                        .build().show()
                }
            }

            executionItem {
                title = "只有一个按钮的多内容dialog"
                execute {
                    YFDialog.Builder(it)
                        .setSingleBtnView(object : SingleBtnDialog() {
                            override fun getTitle(): String =
                                "我是个标题,我是个标题,我是个标题,我是个标题,我是个标题,我是个标题,我是个标题,我是个标题,我是个标题,我是个标题"

                            override fun getMessage(): String =
                                "我是个内容,我是个内容,我是个内容,我是个内容,我是个内容,我是个内容,我是个内容,我是个内容,我是个内容,我是个内容,我是个内容,我是个内容,我是个内容,我是个内容,我是个内容,我是个内容,我是个内容,我是个内容"

                            override fun getButtonText(): String = "知道了"
                        })
                        .build().show()
                }
            }
            executionItem {
                title = "两个按钮的dialog"
                execute {
                    val view = object : DoubleBtnDialog() {
                        override fun getTitle(): String = "我是个标题"
                        override fun getMessage(): String = "我是个内容，我是个内容，我是个内容，我是个内容，我是个内容"
                        override fun getLeftButtonText(): String = "取消"
                        override fun getRightButtonText(): String = "确定"
                        override fun onLeftClickListener(dialog: IDialog) {
                            dialog.dismiss()
                        }

                        override fun onRightClickListener(dialog: IDialog) {
                            dialog.dismiss()
                        }
                    }
                    val dialog = YFDialog.Builder(it)
                        .setDoubleBtnView(view)
                        .setOnShowListener(DialogInterface.OnShowListener {
                            Handler().postDelayed({ view.setMessage("message") }, 1000)
                            Handler().postDelayed({ view.setTitle("title") }, 2000)
                            Handler().postDelayed({ view.setTitle("") }, 3000)

                        })
                        .build()
                    dialog.show()
                }
            }
            executionItem {
                title = "只有图片的dialog"
                execute {
                    YFDialog.Builder(it)
                        .setDoubleBtnView(object : DoubleBtnDialog() {
                            override fun getTopImageResId(): Int? {
                                return R.drawable.dialog_example_bg_top
                            }

                            override fun getLeftButtonText(): String = "取消"
                            override fun getRightButtonText(): String = "确定"
                        })
                        .build().show()
                }
            }
            executionItem {
                title = "有title有message加顶部有图片的dialog"
                execute {
                    YFDialog.Builder(it)
                        .setDoubleBtnView(object : DoubleBtnDialog() {
                            override fun getTopImageResId(): Int? {
                                return R.drawable.dialog_example_bg_top
                            }

                            override fun getTitle(): String = "我是个标题"
                            override fun getMessage(): String =
                                "我是个内容，我是个内容，我是个内容，我是个内容，我是个内容，我是个内容，我是个内容，我是个内容，我是个内容，我是个内容，我是个内容，我是个内容，我是个内容，我是个内容，我是个内容"

                            override fun getLeftButtonText(): String = "取消"
                            override fun getRightButtonText(): String = "确定"
                        })
                        .build().show()
                }
            }
            executionItem {
                title = "只有title加顶部有图片的dialog"
                execute {
                    YFDialog.Builder(it)
                        .setDoubleBtnView(object : DoubleBtnDialog() {
                            override fun getTopImageResId(): Int? {
                                return R.drawable.dialog_example_bg_top
                            }

                            override fun getTitle(): String = "我是个标题"
                            override fun getLeftButtonText(): String = "取消"
                            override fun getRightButtonText(): String = "确定"
                        })
                        .build().show()
                }
            }
            executionItem {
                title = "提示升级对话框"
                execute {
                    YFDialog.Builder(it)
                        .setDoubleBtnView(object : DoubleBtnDialog() {
                            override fun getTopImageResId(): Int? = R.drawable.dialog_bg_upgrade
                            override fun getTitle(): String = "升级弹窗title"
                            override fun getMessage(): String = "升级弹窗message"
                            override fun getLeftButtonText(): String = "leftButton"
                            override fun getRightButtonText(): String = "RightButton"
                        })
                        .build().show()
                }

            }
            executionItem {
                title = "进度条对话框"
                execute {
                    val progressDialog = object : ProgressDialog() {
                        override fun onButtonClick(dialog: IDialog) {
                            dialog.dismiss()
                        }

                        override fun getTitle(): String {
                            return "progressDialog"
                        }

                        override fun getButtonText(): String = "取消"
                    }

                    // 进度为0~100，如需更新进度，可以直接调用 progressDialog.setProgress(50)
                    val a = YFDialog.Builder(it)
                        .setProgressView(progressDialog)
                        .build()
                    a.show()

                    // 定时增加进度
                    GlobalScope.launch {
                        for (progress in 0..100) {
                            delay(100)
                            launch(Dispatchers.Main) {
                                progressDialog.setProgress(progress)
                                progressDialog.setTitle("progressDialog...$progress%")
                            }
                        }
                    }
                }
            }
            executionItem {
                title = "自定义对话框"
                execute {
                    YFDialog.Builder(it)
                        .setCustomView(object : ViewWrapper {
                            override fun getLayoutRes(): Int = R.layout.layout_custom_dialog

                            override fun onViewCreated(view: View, dialog: IDialog) {
                                val btn = view.findViewById<Button>(R.id.btn)
                            }
                        })
                        .build().show()
                }
            }

        }
    }
}

const val DEBUG = true
internal  fun debugLog(value: String) {
    if (DEBUG) {
        Log.d("yf_dialog_app", value)
    }
}