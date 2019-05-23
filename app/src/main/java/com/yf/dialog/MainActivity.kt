package com.yf.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import com.yf.dialog.lib.core.IDialog
import com.yf.dialog.lib.core.YFDialog
import com.yf.dialog.lib.core.view.ViewWrapper
import com.yf.dialog.lib.impl.DoubleBtnDialog
import com.yf.dialog.lib.impl.ProgressDialog
import com.yf.dialog.lib.impl.SingleBtnDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val map = linkedMapOf(
            Pair("测试build属性dialog", View.OnClickListener {
                YFDialog.Builder(this)
                    .setToken("dialog number 1")
                    .setWidth((this@MainActivity.windowManager.defaultDisplay.width.toFloat() * 0.3).toInt())
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
            }),
            Pair("默认dialog", View.OnClickListener {
                YFDialog.Builder(this)
                    .setToken("dialog number 2")
                    .build().show()
            }),
            Pair("只设置title的单按钮dialog", View.OnClickListener {
                YFDialog.Builder(this)
                    .setToken("dialog number 3")
                    .setSingleBtnView(object : SingleBtnDialog() {
                        override fun getTitle(): String = "我是个标题"
                        override fun getButtonText(): String = "知道了"
                    })
                    .build().show()
            }),
            Pair("连续弹出dialog", View.OnClickListener {
                val dialog = YFDialog.Builder(this)
                    .setSingleBtnView(object : SingleBtnDialog() {
                        override fun getTitle(): String = "我是个标题"
                        override fun getButtonText(): String = "知道了"
                        override fun onButtonClick(dialog: IDialog) {
//                            super.onButtonClick(dialog)

                            val dialog1 = YFDialog.Builder(this@MainActivity)
                                .setSingleBtnView(object : SingleBtnDialog() {
                                    override fun getTitle(): String = "我是个标题"
                                    override fun getButtonText(): String = "知道了"
                                })
                                .build()
                            dialog1.show()
                            dialog1.show()

                        }
                    })
                    .build()
                dialog.show()
                dialog.show()
            }),
            Pair("只设置message的单按钮dialog", View.OnClickListener {
                YFDialog.Builder(this)
                    .setToken("dialog number 3")
                    .setSingleBtnView(object : SingleBtnDialog() {
                        override fun getMessage(): String = "我是个标题"
                        override fun getButtonText(): String = "知道了"
                    })
                    .build().show()
            }),
            Pair("只有一个按钮的单行字并且自定义样式dialog", View.OnClickListener {
                YFDialog.Builder(this)
                    .setViewStyle(R.style.MyCustomDialogStyle)
                    .setSingleBtnView(object : SingleBtnDialog() {
                        override fun getTitle(): String = "我是个标题"
                        override fun getButtonText(): String = "知道了"
                    })
                    .build().show()
            }),
            Pair("只有一个按钮的双行字dialog", View.OnClickListener {
                YFDialog.Builder(this)
                    .setSingleBtnView(object : SingleBtnDialog() {
                        override fun getTitle(): String = "我是个标题"
                        override fun getMessage(): String = "我是个内容"
                        override fun getButtonText(): String = "知道了"
                        override fun onButtonClick(dialog: IDialog) {
                            dialog.dismiss()
                        }
                    })
                    .build().show()
            }),
            Pair("只有一个按钮的多内容dialog", View.OnClickListener {
                YFDialog.Builder(this)
                    .setSingleBtnView(object : SingleBtnDialog() {
                        override fun getTitle(): String =
                            "我是个标题,我是个标题,我是个标题,我是个标题,我是个标题,我是个标题,我是个标题,我是个标题,我是个标题,我是个标题"

                        override fun getMessage(): String =
                            "我是个内容,我是个内容,我是个内容,我是个内容,我是个内容,我是个内容,我是个内容,我是个内容,我是个内容,我是个内容,我是个内容,我是个内容,我是个内容,我是个内容,我是个内容,我是个内容,我是个内容,我是个内容"

                        override fun getButtonText(): String = "知道了"
                    })
                    .build().show()
            }),
            Pair("两个按钮的dialog", View.OnClickListener {
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
                val dialog = YFDialog.Builder(this)
                    .setDoubleBtnView(view)
                    .setOnShowListener(DialogInterface.OnShowListener {
                        Handler().postDelayed({ view.setMessage("message") }, 1000)
                        Handler().postDelayed({ view.setTitle("title") }, 2000)
                        Handler().postDelayed({ view.setTitle("") }, 3000)

                    })
                    .build()
                dialog.show()

            }),
            Pair("只有图片的dialog", View.OnClickListener {
                YFDialog.Builder(this)
                    .setDoubleBtnView(object : DoubleBtnDialog() {
                        override fun getTopImageResId(): Int? {
                            return R.drawable.dialog_example_bg_top
                        }

                        override fun getLeftButtonText(): String = "取消"
                        override fun getRightButtonText(): String = "确定"
                    })
                    .build().show()
            }),
            Pair("有title有message加顶部有图片的dialog", View.OnClickListener {
                YFDialog.Builder(this)
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
            }),
            Pair("只有title加顶部有图片的dialog", View.OnClickListener {
                YFDialog.Builder(this)
                    .setDoubleBtnView(object : DoubleBtnDialog() {
                        override fun getTopImageResId(): Int? {
                            return R.drawable.dialog_example_bg_top
                        }

                        override fun getTitle(): String = "我是个标题"
                        override fun getLeftButtonText(): String = "取消"
                        override fun getRightButtonText(): String = "确定"
                    })
                    .build().show()
            }),
            Pair("提示升级对话框", View.OnClickListener {
                YFDialog.Builder(this)
                    .setDoubleBtnView(object : DoubleBtnDialog() {
                        override fun getTopImageResId(): Int? = R.drawable.dialog_bg_upgrade
                        override fun getTitle(): String = "升级弹窗title"
                        override fun getMessage(): String = "升级弹窗message"
                        override fun getLeftButtonText(): String = "leftButton"
                        override fun getRightButtonText(): String = "RightButton"
                    })
                    .build().show()


            }),
            Pair("进度条对话框", View.OnClickListener {
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
                val a = YFDialog.Builder(this)
                    .setProgressView(progressDialog)
                    .build()
                a.show()

                // 定时增加进度
                GlobalScope.launch {
                    for (progress in 0..100) {
                        delay(500)
                        debugLog("thread on ${Thread.currentThread().name}")
                        launch(Dispatchers.Main) {
                            debugLog("thread on ${Thread.currentThread().name}")
                            progressDialog.setProgress(progress)
                        }
                    }
                }
            }),
            Pair("自定义对话框", View.OnClickListener {
                YFDialog.Builder(this)
                    .setCustomView(object : ViewWrapper {
                        override fun getLayoutRes(): Int = R.layout.layout_custom_dialog

                        override fun onViewCreated(view: View, dialog: IDialog) {
                            val btn = view.findViewById<Button>(R.id.btn)
                        }
                    })
                    .build().show()
            })
        )
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, map.keys.toList())
        listView.setOnItemClickListener { parent, view, position, id ->
            map[map.keys.toList()[position]]?.onClick(view)
        }
    }
}