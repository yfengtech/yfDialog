package com.yf.dialog.lib.core.view

import android.support.v7.widget.CardView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.yf.dialog.lib.core.IDialog
import com.yf.dialog.lib.core.Utils
import com.yf.dialog.library.R


/**
 * 这个类是dialog库提供的默认实现，样式为顶部图片和title和message以及多个按钮组成，
 * 已有的实现类，单个按钮[com.yf.dialog.lib.impl.SingleBtnDialog]，
 * 两个按钮[com.yf.dialog.lib.impl.DoubleBtnDialog]，
 *
 * @author yf
 * @date 2019/4/22
 */
abstract class AbsDefaultDialog : ViewWrapper {
    /**
     * 最外层cardView布局
     */
    private lateinit var cardViewBg: CardView
    /**
     * title和message的文本区域布局
     */
    private lateinit var llTextContent: LinearLayout

    private lateinit var tvTitle: TextView
    private lateinit var tvMessage: TextView

    /**
     * 顶部的图片
     */
    private lateinit var ivTop: ImageView

    /**
     * 提供的默认布局样式layout
     */
    override fun getLayoutRes(): Int = R.layout.dialoglib_dialog_sample_default

    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, dialog: IDialog) {
        cardViewBg = view.findViewById(R.id.cardViewBg)
        ivTop = view.findViewById(R.id.ivTop)
        llTextContent = view.findViewById(R.id.llTextContent)
        tvTitle = view.findViewById(R.id.tvTitle)
        tvMessage = view.findViewById(R.id.tvMessage)
        // 设置卡片属性
        cardViewBg.radius = getBackgroundRadius()
        /**
         * 设置顶部顶部图片
         */
        if (getTopImageResId() == null) {
            ivTop.visibility = View.GONE
        } else {
            // 如果顶部有图片，重新计算顶部距离
            val newPaddingTop = llTextContent.paddingTop - 30
            if (newPaddingTop > 0) {
                llTextContent.setPadding(
                    llTextContent.paddingLeft,
                    Utils.dp2px(newPaddingTop.toFloat()),
                    llTextContent.paddingRight,
                    llTextContent.paddingBottom
                )
            }
            ivTop.setImageResource(getTopImageResId()!!)
            ivTop.visibility = View.VISIBLE
        }
        // 初始化title和message
        updateTextContent(getTitle(), getMessage())
    }

    /**
     * 动态设置title
     */
    fun setTitle(title: String) {
        updateTextContent(title = title)
    }

    /**
     * 动态设置message
     */
    fun setMessage(message: String) {
        updateTextContent(message = message)
    }

    /**
     * 更新title或message文本内容，如果只有message，没有title。将message赋值给title
     */
    private fun updateTextContent(
        title: String = tvTitle.text.toString(),
        message: String = tvMessage.text.toString()
    ) {
        var newTitle = title
        var newMessage = message
        // 如果只有message，没有title。将message赋值给title
        if (newTitle.isBlank() && newMessage.isNotBlank()) {
            newTitle = message
            newMessage = ""
        }

        if (newTitle.isBlank() && newMessage.isBlank()) {
            llTextContent.visibility = View.GONE
        } else {
            llTextContent.visibility = View.VISIBLE
            if (newTitle.isNotBlank()) {
                tvTitle.visibility = View.VISIBLE
                tvTitle.text = newTitle
                if (newMessage.isBlank()) {
                    // 如果只有title，字号减小2号
                    tvTitle.textSize = 22f
                }
            } else {
                tvTitle.visibility = View.GONE
            }
            if (newMessage.isNotBlank()) {
                tvMessage.text = newMessage
                tvMessage.visibility = View.VISIBLE
            } else {
                tvMessage.visibility = View.GONE
            }
        }
    }

    /**
     * 外部重写该方法，初始化顶部的imageView显示图片
     */
    open fun getTopImageResId(): Int? = null

    /**
     * 整个view背景圆角
     */
    open fun getBackgroundRadius() = Utils.dp2px(8f).toFloat()

    /**
     * 外部可以重写，用来初始化title文本
     */
    open fun getTitle(): String = ""

    /**
     * 外部可以重写，用来初始化message文本
     */
    open fun getMessage(): String = ""
}