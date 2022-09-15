package com.app.donateclaim

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.Gravity


class ProgressUtil : Dialog {

    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, theme: Int) : super(context!!, theme)

    companion object {
        private var dialog: ProgressUtil? = null
        fun show(
            context: Context?, cancelable: Boolean,
            cancelListener: DialogInterface.OnCancelListener?
        ): ProgressUtil {
            dialog = ProgressUtil(context, R.style.ProgressUtil)
            dialog?.setTitle("")
            dialog?.setContentView(R.layout.progress_util)
            dialog?.setCancelable(cancelable)
            dialog?.setOnCancelListener(cancelListener)
            dialog?.window!!.attributes.gravity = Gravity.CENTER
            val lp = dialog!!.window!!.attributes
            lp.dimAmount = 0.6f
            dialog!!.window!!.attributes = lp
            dialog!!.show()
            return dialog!!
        }
    }
}