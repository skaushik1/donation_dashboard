package com.app.donateclaim.base

import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.app.donateclaim.helper.ProgressUtil
import com.app.donateclaim.R
import com.app.donateclaim.helper.PrefData


import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActivity : AppCompatActivity() {

    private var alertDialog: AlertDialog? = null

    private var progressUtil: ProgressUtil? = null


    lateinit var localPref: PrefData
    val compositeDisposable = CompositeDisposable()


    lateinit var mcontext: Activity


    override fun onCreate(savedInstanceState: Bundle?) {
        if (isAndroidTV()) {
            requestWindowFeature(Window.FEATURE_OPTIONS_PANEL)
        }
        super.onCreate(savedInstanceState)
        adjustFontScale(resources.configuration)
        mcontext = this
        localPref = PrefData(this)
        progressUtil = ProgressUtil(this)
        initializeLoader()
    }

    private fun initializeLoader() {
    }

    /**
     * This method is used to stop scaling the font size from device setting font size
     */
    private fun adjustFontScale(configuration: Configuration) {
        configuration.fontScale = 1.0.toFloat()
        val metrics = resources.displayMetrics
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity = configuration.fontScale * metrics.density
        baseContext.resources.updateConfiguration(configuration, metrics)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    private fun isAndroidTV(): Boolean {
        val uiModeManager = getSystemService(UI_MODE_SERVICE) as UiModeManager
        return uiModeManager.currentModeType == Configuration.UI_MODE_TYPE_TELEVISION
    }

    protected fun Disposable.autoDispose() {
        compositeDisposable.add(this)
    }

    /**
     * To show Toast message in app ver 1
     */
    fun showToastVer1(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * To show Toast message in app
     */
    fun showToast(
        message: String?,
        onPositiveButtonClickListener: DialogInterface.OnClickListener? = null,
        onNegativeButtonClickListener: DialogInterface.OnClickListener? = null
    ) {
        showAlert(
            message = message,
            onPositiveButtonClickListener = onPositiveButtonClickListener,
            onNegativeButtonClickListener = onNegativeButtonClickListener
        )
    }

    /**
     * This method is used to show default alert for app by passing message.
     */
    private fun showAlert(
        title: String? = null,
        message: String?,
        positiveButtonText: String? = getString(R.string.ok),
        negativeButtonText: String? = getString(R.string.cancel),
        onPositiveButtonClickListener: DialogInterface.OnClickListener? = null,
        onNegativeButtonClickListener: DialogInterface.OnClickListener? = null
    ) {
        this.runOnUiThread {
            if (title == null) {
                val alertDialogTemp = AlertDialog.Builder(this)
                alertDialogTemp.setMessage(message)
                alertDialogTemp.setPositiveButton(positiveButtonText, onPositiveButtonClickListener)
                if (onNegativeButtonClickListener != null) {
                    alertDialogTemp.setNegativeButton(
                        negativeButtonText,
                        onNegativeButtonClickListener
                    )
                }
                alertDialog = alertDialogTemp.create()
                alertDialog?.show()

            } else {

                val alertDialogTemp = AlertDialog.Builder(this)
                alertDialogTemp.setTitle(title)
                alertDialogTemp.setMessage(message)
                alertDialogTemp.setPositiveButton(positiveButtonText, onPositiveButtonClickListener)
                if (onNegativeButtonClickListener != null) {
                    alertDialogTemp.setNegativeButton(
                        negativeButtonText,
                        onNegativeButtonClickListener
                    )
                }
                alertDialog = alertDialogTemp.create()
                alertDialog?.show()
            }
        }
    }

    /*
     * This method is used to show loader
     */
    fun showProgress(mContext: Context) {
        try {
            if (!progressUtil?.isShowing!!) {
                progressUtil = ProgressUtil.show(mContext, false, null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /*
     * This method is used to hide loader
     */
    fun hideProgress() {
        try {
            progressUtil?.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}