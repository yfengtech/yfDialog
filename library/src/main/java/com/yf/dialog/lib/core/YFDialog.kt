package com.yf.dialog.lib.core

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.annotation.StyleRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentActivity
import android.view.*
import com.yf.dialog.lib.core.view.ViewWrapper
import com.yf.dialog.lib.impl.DefaultViewDialog
import com.yf.dialog.lib.impl.DoubleBtnDialog
import com.yf.dialog.lib.impl.ProgressDialog
import com.yf.dialog.lib.impl.SingleBtnDialog


@Suppress("DEPRECATION")
class YFDialog : DialogFragment(), IDialog, DialogInterface.OnShowListener {

    /**
     * dialog中的布局
     */
    private lateinit var mContextView: ViewWrapper
    /**
     * 根据ViewWrapper生成的View实例
     */
    private var mView: View? = null
    /**
     * dialog的控制器，包含dialog的各种参数
     */
    private var mController = DialogController()

    override fun isShowing(): Boolean = dialog?.isShowing ?: false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 检测样式，并赋值默认样式
        mController.context?.checkDialogThemeByCustom(mController.viewStyle ?: 0)
    }

    /**
     * 创建view方法，同时初始化一些dialog的属性
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initDialogProp(this.dialog)
        mContextView = mController.viewWrapper!!
        mView = inflater.inflate(mContextView.getLayoutRes(), container)
        mView!!.layoutParams =
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return mView
    }

    /**
     * view创建完成的回调，回调给外部接口，对view做一些操作
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContextView.onViewCreated(mView!!, this)
    }

    /**
     * ====================回调监听start=======================
     */
    override fun onCancel(dialog: DialogInterface?) {
        super.onCancel(dialog)
        YFDialogManager.dispatchDialogCancel(this, mController.context, mController.token)
        mController.cancelListener?.onCancel(dialog)
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        YFDialogManager.dispatchDialogDismiss(this, mController.context, mController.token)
        mController.dismissListener?.onDismiss(dialog)
    }

    override fun onShow(dialog: DialogInterface?) {
        YFDialogManager.dispatchDialogShow(this, mController.context, mController.token)
        mController.showListener?.onShow(dialog)
    }
    /**
     * ====================回调监听end=======================
     */


    /**
     * 显示dialog
     */
    override fun show() {
        show(mController.fragmentManager, mController.context?.javaClass?.simpleName ?: "default_tag")
    }

    override fun onStart() {
        super.onStart()
        dialog.apply {
            window?.apply {
                // 设置window属性
                if (mController.animRes > 0)
                    setWindowAnimations(mController.animRes)
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                attributes = attributes.apply {
                    gravity = mController.gravity
                    dimAmount = mController.dimAmount
                    if (mController.dialogWidth > 0) width = mController.dialogWidth
                    if (mController.dialogHeight > 0) height = mController.dialogHeight
                }
            }
        }
    }

    /**
     * 初始化dialog属性和样式，例如外部是否可以点击等
     */
    private fun initDialogProp(dialog: Dialog) {
        mController.viewStyle?.let { mController.context?.setTheme(it) }
        dialog.apply {
            setCanceledOnTouchOutside(mController.isCanceledOnTouchOutside)
            setCancelable(mController.isCanceledOnBack)
            setOnShowListener(this@YFDialog)
            setOnKeyListener(object : DialogInterface.OnKeyListener {
                override fun onKey(dialog: DialogInterface?, keyCode: Int, event: KeyEvent): Boolean {
                    return keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN && !mController.isCanceledOnBack
                }
            })
        }
    }

    class Builder(context: Context) {

        private var mParams: DialogController.Params

        init {
            if (context !is FragmentActivity) {
                throw IllegalArgumentException("context必须是FragmentActivity")
            }
            mParams = DialogController.Params()
            mParams.context = context
            mParams.fragmentManager = context.supportFragmentManager
            setDefaultOption()
        }

        fun setViewStyle(@StyleRes style: Int): Builder {
            mParams.viewStyle = style
            return this
        }

        /**
         * 设置dialog的宽度
         */
        fun setWidth(width: Int): Builder {
            mParams.dialogWidth = width
            return this
        }

        /**
         * 设置dialog的高度
         */
        fun setHeight(height: Int): Builder {
            mParams.dialogHeight = height
            return this
        }

        /**
         * 设置dialog外部的区域透明度，
         *
         * @param dimAmount 透明度：`0.0~1.0`表示`完全透明~不透明`
         */
        fun setDimAmount(dimAmount: Float): Builder {
            mParams.dimAmount = dimAmount
            return this
        }

        /**
         * 设置dialog在屏幕中的显示位置
         *
         * @param gravity dialog的显示位置，具体设置参数查看[Gravity]
         */
        fun setGravity(gravity: Int): Builder {
            mParams.gravity = gravity
            return this
        }

        /**
         * 设置dialog显示时动画
         *
         * @param animRes dialog的显示动画资源文件
         */
        fun setAnimStyleRes(animStyleRes: Int): Builder {
            mParams.animRes = animStyleRes
            return this
        }

        /**
         * 设置该dialog为一个按钮的默认布局
         */
        fun setSingleBtnView(view: SingleBtnDialog): Builder {
            mParams.viewWrapper = view
            return this
        }

        /**
         * 设置该dialog为两个按钮的默认布局
         */
        fun setDoubleBtnView(view: DoubleBtnDialog): Builder {
            mParams.viewWrapper = view
            return this
        }

        /**
         * 设置该dialog为进度条布局
         */
        fun setProgressView(view: ProgressDialog): Builder {
            mParams.viewWrapper = view
            return this
        }

        /**
         * 需要自定义布局是调用，传入一个实现了[com.yf.dialog.lib.core.view.ViewWrapper]接口的实例对象
         *
         * @param customView 自定义的布局
         */
        fun setCustomView(customView: ViewWrapper): Builder {
            mParams.viewWrapper = customView
            return this
        }

        /**
         * 设置点击dialog外部时，dialog是否关闭
         *
         * @param b `true`点击dialog外部时关闭dialog，`false`点击dialog外部时不关闭dialog
         */
        fun setCanceledOnTouchOutside(b: Boolean): Builder {
            mParams.isCanceledOnTouchOutside = b
            return this
        }

        /**
         * 设置点击返回键时，dialog否关闭
         *
         * @param b `true`点击返回键时关闭dialog，`false`点击返回键时不关闭dialog
         */
        fun setCanceledOnBack(b: Boolean): Builder {
            mParams.isCanceledOnBack = b
            return this
        }

        /**
         * 设置dialog的唯一标识token，可以进行全局配置，
         */
        fun setToken(token: String): Builder {
            mParams.token = token
            return this
        }

        /**
         * 设置dismiss回调监听，在dialog销毁的时候会调用dismiss
         */
        fun setOnDismissListener(dismissListener: DialogInterface.OnDismissListener): Builder {
            mParams.dismissListener = dismissListener
            return this
        }

        /**
         * 设置show回调监听，在dialog销毁的时候会调用dismiss
         */
        fun setOnShowListener(showListener: DialogInterface.OnShowListener): Builder {
            mParams.showListener = showListener
            return this
        }

        /**
         * 设置show回调监听，在dialog销毁的时候会调用dismiss
         */
        fun setOnCalcelListener(cancelListener: DialogInterface.OnCancelListener): Builder {
            mParams.cancelListener = cancelListener
            return this
        }

        /**
         * 设置默认参数，外部设置会覆盖此设置
         */
        private fun setDefaultOption() {
            mParams.dimAmount = 0.5f
            val display = mParams.context!!.resources.displayMetrics
            mParams.dialogWidth = if (display.widthPixels >= display.heightPixels) {
                // 横屏设备
                (display.widthPixels.toFloat() * 0.35).toInt()
            } else {
                // 竖屏设备
                (display.widthPixels.toFloat() * 0.7).toInt()
            }

            mParams.dialogHeight = WindowManager.LayoutParams.WRAP_CONTENT
            mParams.viewWrapper = DefaultViewDialog()
//            mParams.animRes = R.style.DialogDefaultAnimation
        }

        /**
         * 构造dialog实例对象，用于显示和隐藏
         * 构造的dialog会有初始的默认属性，自定义属性会覆盖其默认属性
         */
        fun build(): IDialog {
            var dialog = YFDialog()
            mParams.apply(dialog.mController)
            return dialog
        }
    }
}