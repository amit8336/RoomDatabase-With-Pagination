package com.example.roomdatabase.pagination.Pagination

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.TextView
import com.example.roomdatabase.pagination.R

object jun_u_dialog {
    var dialog: Dialog? = null
    @SuppressLint("SetTextI18n")
    fun pdialog(context: Activity) {
        try {
            if (!context.isFinishing()) {
                if (dialog != null) if (dialog!!.isShowing) dialog!!.dismiss()
                dialog = Dialog(context)
                dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog!!.setContentView(R.layout.jun_custom_progressbar)
                dialog!!.setCancelable(false)
                val textView: TextView = dialog!!.findViewById<TextView>(R.id.txtMsg)
                textView.setText("Loading")
                dialog!!.show()
            }
        } catch (e: Error) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun pdialog_dismiss() {
        try {
            if (dialog != null && dialog!!.isShowing) dialog!!.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}