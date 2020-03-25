package com.edlo.demodrama.ui.base

import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.edlo.demodrama.R
import com.edlo.demodrama.ui.LoadingDialog

abstract class BaseActivity<VM: BaseViewModel>: AppCompatActivity() {

    lateinit var viewModel: VM

    lateinit var loadingDialog: LoadingDialog
    fun getLoadDialog(): LoadingDialog = loadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = initViewModel()
        loadingDialog = LoadingDialog(this, R.style.LoadingDialog)
    }

    abstract fun initViewModel(): VM
    abstract fun getActivityViewModel(): VM

//    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
//        if (ev.action == MotionEvent.ACTION_DOWN) {
//            val v = currentFocus
//            if (isShouldHideKeyboard(v, ev)) {
//                val res = hideKeyboard(v!!.windowToken)
//                if (res) {
//                    return true
//                }
//            }
//        }
//        return super.dispatchTouchEvent(ev)
//    }
//
//    /**
//     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
//     *
//     * @param v
//     * @param event
//     * @return
//     */
//    private fun isShouldHideKeyboard(
//        v: View?,
//        event: MotionEvent
//    ): Boolean {
//        if (v != null && v is EditText) {
//            val l = intArrayOf(0, 0)
//            v.getLocationInWindow(l)
//            val left = l[0]
//            val top = l[1]
//            val bottom = top + v.getHeight()
//            val right = left + v.getWidth()
//            return !(event.x > left && event.x < right && event.y > top && event.y < bottom)
//        }
//        return false
//    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
     fun hideKeyboard(token: IBinder?): Boolean {
        if (token != null) {
            val im =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            return im.hideSoftInputFromWindow(
                token,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
        return false
    }
}