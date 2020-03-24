package com.edlo.demovideolistwithroom.ui

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.view.LayoutInflater
import com.edlo.demovideolistwithroom.R

class LoadingDialog(context: Context, themeResId: Int): Dialog(context, themeResId) {

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null)
        setContentView(view)
        setCancelable(false)
        window?.let { w ->
            val layoutParams = w.attributes
            layoutParams.x = 0
            layoutParams.y = 0
            layoutParams.width = context.resources.displayMetrics.widthPixels
            w.attributes = layoutParams
        }
    }

    fun setIsProgressing(progressing: Boolean) {
        val activity = getContextAsActivity()
        if (activity == null) {
            dismiss()
        } else {
            if(!activity.isFinishing && !activity.isDestroyed) {
                if (progressing) {
                    show()
                } else {
                    dismiss()
                }
            }
        }
    }
//    fun reset() { }

    private fun getContextAsActivity(): Activity? {
        var baseContext = (context as ContextWrapper).baseContext
        var result: Activity? = null
        if(baseContext is Activity) {
            result = baseContext
        }
        return result
    }
}
