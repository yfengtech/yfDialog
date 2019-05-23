package com.yf.dialog.lib.core.view

import android.view.View
import com.yf.dialog.lib.core.IDialog

/**
 * dialog库的核心view接口，已经提供的默认样式已经实现此接口，调用者不需要关心
 * 如果调用者需要自定义布局，需要实现此接口，然后调用[com.yf.dialog.lib.core.YFDialog.Builder.setCustomView]传递进来
 *
 * @author yf
 * @date 2019/4/22
 */
interface ViewWrapper {

    /**
     * 获取dialog生成view所需的布局文件，需要外部传递
     *
     * @return
     */
    fun getLayoutRes(): Int

    /**
     * view创建完成的回调，在此函数中可以对view进行操作，设置属性或者点击事件等操作
     *
     * @param view 是根据LayoutRes生成的view
     * @param dialog 回调给外部的dialog，可以进行dismiss等操作
     */
    fun onViewCreated(view: View, dialog: IDialog)
}