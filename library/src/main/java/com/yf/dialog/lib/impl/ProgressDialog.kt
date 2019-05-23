package com.yf.dialog.lib.impl

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.yf.dialog.lib.core.IDialog
import com.yf.dialog.lib.core.view.ViewWrapper
import com.yf.dialog.library.R

/**
 * 默认提供的进度条dialog
 *
 * @author yf
 * @date 2019/4/24
 */
abstract class ProgressDialog : ViewWrapper {

    private var progressBar: ProgressBar? = null
    private var tvTitle: TextView? = null

    override fun getLayoutRes(): Int {
        return R.layout.dialoglib_dialog_sample_progress
    }

    override fun onViewCreated(view: View, dialog: IDialog) {
        val button = view.findViewById<TextView>(R.id.button)
        tvTitle = view.findViewById(R.id.tvTitle)
        progressBar = view.findViewById(R.id.progressBar)
        tvTitle?.text = getTitle()
        button.text = getButtonText()
        button.setOnClickListener { onButtonClick(dialog) }
    }

    abstract fun getTitle(): String
    abstract fun getButtonText(): String
    abstract fun onButtonClick(dialog: IDialog)

    fun setTitle(title: String) {
        tvTitle?.text = title
    }

    fun setProgress(progress: Int) {
        progressBar?.progress = progress
    }
}