package com.app.donateclaim.constant

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.widget.ContentLoadingProgressBar

object AppMethods {

    /**
     * This method is used to enable the button, change alpha and progress
     */
    fun setInputButtonEnable(view: View, progress: ContentLoadingProgressBar) {
        progress.visibility = View.GONE
        view.isEnabled = true
        view.alpha = 1f
    }

    /**
     * This method is used to disable the button, change alpha and progress
     */
    fun setInputButtonDisable(view: View, progress: ContentLoadingProgressBar) {
        progress.visibility = View.VISIBLE
        view.isEnabled = false
        view.alpha = 0.5f
    }

    /**
     * This method is used to only enable the button and change alpha
     * @param view
     */
    fun setInputButtonEnable(view: View) {
        view.isEnabled = true
        view.alpha = 1f
    }

    /**
     * This method is used to only disable the button and change alpha
     * @param view
     */
    fun setInputButtonDisable(view: View) {
        view.isEnabled = false
        view.alpha = 0.5f
    }

    /**
     * This method is used to enable the loader and disable the button with alpha
     */
    fun setLoaderEnable(btnView: View, progress: View) {
        progress.visibility = View.VISIBLE
        btnView.isEnabled = false
        btnView.alpha = 0.5f
    }

    /**
     * This method is used to disable the loader and enable button with alpha
     */
    fun setLoaderDisable(btnView: View, progress: View) {
        progress.visibility = View.GONE
        btnView.isEnabled = true
        btnView.alpha = 1f
    }

    /**
     * This method is used to enable the loader only
     */
    fun setOnlyLoaderEnable(progress: View) {
        progress.visibility = View.VISIBLE
    }

    /**
     * This method is used to disable the loader only
     */
    fun setOnlyLoaderDisable(progress: View) {
        progress.visibility = View.GONE
    }

    /**
     * This method is used to hide soft keyboard
     */
    fun hideKeyboardFrom(activity: Activity) {
        val imm =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(getRootView(activity).windowToken, 0)
    }

    /**
     * This method is used to get root view for activity
     */
    fun getRootView(activity: Activity): View {
        return activity.findViewById<View>(android.R.id.content)
    }

    /**
     * This method is used to restrict user from copy paste feature in edit text.
     */
    fun disableCopyPaste(editText: EditText) {
        editText.isLongClickable = false
        editText.setTextIsSelectable(false)

        editText.customSelectionActionModeCallback = object : ActionMode.Callback {
            override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onActionItemClicked(actionMode: ActionMode?, item: MenuItem?): Boolean {
                return false
            }

            override fun onDestroyActionMode(actionMode: ActionMode?) {}
        }
    }

    /**
     * Get Screen Width
     * @return int
     */
    val screenWidth: Int
        get() = Resources.getSystem().displayMetrics.widthPixels

    /**
     * Get Screen height
     * @return int
     */
    val screenHeight: Int
        get() = Resources.getSystem().displayMetrics.heightPixels

    /**
     * This method is used for hiding soft keyboard
     */
    fun hideSoftInput(view: View, activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * This method is used for showing softkeyboard
     */
    fun showSoftInput(view: View, activity: Activity) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, 0)
    }

    /**
     * Check if internet connection available.
     * @param context - context
     * @return - true if network is availalbe
     */
    fun isNetworkConnected(context: Context): Boolean {
        val networkInfo =
            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        return networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected
    }

    /**
     * This method is used to check internet connection
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            return connectivityManager.activeNetworkInfo?.isConnected ?: false
        }
    }
}