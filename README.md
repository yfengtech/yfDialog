# YFDialog

## 设计思想
* 采用DialogFragment实现，基于fragment，复用性更好
* window的设置和view的设置分离开
window的设置使用builder构建者模式来实现
view采用抽象样式抛给外部实现的方式
* 提供接口ViewWrapper用来自定义布局

## 使用方式
* 依赖dialog的library
```
implementation 'com.github.moonlight920:yfdialog:1.0.0'
```
* 场景1：单个按钮的dialog  
```kotlin
YFDialog.Builder(this)
    .setSingleBtnView(object : SingleBtnDialog() {
        override fun getTitle(): String = "单按钮dialog标题"
        override fun getMessage(): String = "单按钮dialog内容"
        override fun getButtonText(): String = "知道了"
        override fun onButtonClick(dialog: IDialog) {
            dialog.dismiss()
        }
    })
    .build().show()
```
* 场景2：两个按钮的dialog
```kotlin
YFDialog.Builder(this)
    .setDoubleBtnView(object : DoubleBtnDialog() {
        override fun getTitle(): String = "双按钮dialog标题"
        override fun getMessage(): String = "双按钮dialog标题"
        override fun getLeftButtonText(): String = "取消"
        override fun getRightButtonText(): String = "确定"
        override fun onLeftClickListener(dialog: IDialog) {
            dialog.dismiss()
        }

        override fun onRightClickListener(dialog: IDialog) {
            dialog.dismiss()
        }
    })
    .build().show()
```
* 场景3：顶部有图片的dialog
```kotlin
YFDialog.Builder(this)
    .setDoubleBtnView(object : DoubleBtnDialog() {
        override fun getTopImageResId(): Int? {
            return R.drawable.dialog_example_bg_top
        }
        override fun getTitle(): String = "带图片dialog标题"
        override fun getMessage(): String ="带图片dialog内容"
        override fun getLeftButtonText(): String = "取消"
        override fun getRightButtonText(): String = "确定"
        override fun onLeftClickListener(dialog: IDialog) {
            dialog.dismiss()
        }

        override fun onRightClickListener(dialog: IDialog) {
            dialog.dismiss()
        }
    })
    .build().show()
```
* 场景4：需要设置进度的dialog
```kotlin
val progressDialog = object : ProgressDialog() {
    override fun onButtonClick(dialog: IDialog) {
        dialog.dismiss()
    }
    override fun getTitle(): String = "progressDialog"
    override fun getButtonText(): String = "取消"
}
// 进度为0~100，如需更新进度，可以直接调用 progressDialog.setProgress(50)
YFDialog.Builder(this)
    .setProgressView(progressDialog)
    .build().show()
```

* 场景5：需要设置window的属性
```kotlin
YFDialog.Builder(this)
        .setWidth((this@MainActivity.windowManager.defaultDisplay.width.toFloat() * 0.3).toInt())
        .setCanceledOnBack(false)
        .setCanceledOnTouchOutside(false)
        .setDimAmount(0.5f)
        .setGravity(Gravity.CENTER)
        .setOnDismissListener(object : OnDismissListener {
            override fun onDismiss() {

            }
        })
        .setSingleBtnView(object : SingleBtnDialog() {
            override fun getTitle(): String = "我是个标题"
            override fun getButtonText(): String = "知道了"
        })
        .build().show()
```
## 自定义样式
库中提供的默认样式无法满足需求，可以自定义样式
使用方式如下

在`values/styles.xml`文件中定义一个style，parent为默认样式`DefaultDialogStyle`，然后内部添加想要覆盖的样式
```xml
<resources>
   
    <!--app自定义的dialogStyle-->
    <style name="MyCustomDialogStyle" parent="DefaultDialogStyle">
        <!--覆盖button样式-->
        <item name="dialogButton">@style/MyDialogButtonStyle</item>
        <!--覆盖title样式-->
        <item name="dialogTitle">@style/MyDialogTitleStyle</item>
    </style>

    <style name="MyDialogTitleStyle" parent="DialogDefaultTitleStyle">
        <item name="android:textSize">30sp</item>
        <item name="android:textColor">@android:color/holo_purple</item>
    </style>
    <style name="MyDialogButtonStyle" parent="DialogDefaultButton">
        <item name="android:background">@drawable/dialoglib_shape_btn_1</item>
    </style>
</resources>
```
[点此查看可供覆盖的样式，可被覆写](https://github.com/moonlight920/yfDialog/blob/63eec254071fadfd62bbb44b596f6de538bfec24/library/src/main/res/values/styles.xml)
```
在调用dialog的地方，使用`setViewStyle(int style)`,例如
```kotlin
YFDialog.Builder(this)
        .setViewStyle(R.style.MyCustomDialogStyle)
        .setSingleBtnView(object : SingleBtnDialog() {
            override fun getTitle(): String = "我是个标题"
            override fun getButtonText(): String = "知道了"
        })
        .build().show()
```

## lifycycle
用来监听此dialog的全局生命周期回调，可以给dialog设置标识token，从回调中获取，使用方式如下
```kotlin
YFDialog.Builder(this)
        .setToken("token123")
        .setSingleBtnView(object : SingleBtnDialog() {
            override fun getTitle(): String = "我是个标题"
            override fun getButtonText(): String = "知道了"
        })
        .build().show()

// 可以在application设置全局监听
val callback = object : DialogLifecycleCallbacks{
    override fun onDialogShow(dialog: IDialog?,context: Context?, token: String?) {
        debugLog("onDialogShow$token")
    }

    override fun onDialogDismiss(dialog: IDialog?,context: Context?, token: String?) {
        debugLog("onDialogDismiss$token")
    }

    override fun onDialogCancel(dialog: IDialog?,context: Context?, token: String?) {
        debugLog("onDialogCancel$token")
    }
}
// 注册监听
YFDialogManager.registerLifeCycle(callback)
// 取消注册
YFDialogManager.unregisterLifeCycle(callback)
```