package com.app.donateclaim

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseFragment(private val socketConnect: Boolean = true) : Fragment() {

    protected val compositeDisposable = CompositeDisposable()
    lateinit var activity: Activity
    lateinit var baseActivity: BaseActivity
//    lateinit var localPref: PrefData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (activity is BaseActivity) {
            baseActivity = activity as BaseActivity
        }

        initView()
    }

    private fun initView() {
        //localPref = PrefData(requireActivity())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.let {
            activity = (context as Activity)
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    /**
     * To show Toast message in app
     */
    protected fun showToastVer1(msg: String?) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).showToastVer1(msg)
        }
    }

    protected fun Disposable.autoDispose() {
        compositeDisposable.add(this)
    }


    /**
     * To show Toast message in app with positive and negative button
     */
    fun showToast(
        msg: String?,
        onPositiveButtonClickListener: DialogInterface.OnClickListener? = null,
        onNegativeButtonClickListener: DialogInterface.OnClickListener? = null
    ) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).showToast(
                message = msg,
                onPositiveButtonClickListener = onPositiveButtonClickListener,
                onNegativeButtonClickListener = onNegativeButtonClickListener
            )
        }
    }

    /**
     * This method is used to redirect to URL Specified
     */
    fun redirectToURL(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    /**
     * This method is used to dial phone number and allow user to make call from default phone app
     */
    fun makeCall(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phone")
        startActivity(intent)
    }

    /**
     * This method is used to open app specific settings for enabling location permission
     */
    fun openAppSpecificSettings() {
        startActivity(
            Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", activity.packageName, null)
            )
        )
    }


}