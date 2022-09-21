package com.app.donateclaim

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.app.donateclaim.api.ApiCallInterface
import com.app.donateclaim.api.RetrofitClient
import com.app.donateclaim.constant.AppConstant
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging


class MyApplication : MultiDexApplication(), LifecycleObserver {

    private val TAG: String = MyApplication::class.java.simpleName.toString()

    var apiClient: ApiCallInterface? = null





//    fun getApiClient() = apiClient

    companion object {
        lateinit var context: Context
        var apiClient: ApiCallInterface? = null

        private lateinit var myInstance: MyApplication
        fun getInstance(): MyApplication = myInstance
//
//        fun getDeviceToken() {
//            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
//                if (!task.isSuccessful) {
//                    return@OnCompleteListener
//                }
//                val token = task.result
//                Log.d("token123", token.toString())
//                AppConstant.DefaultValues.DeviceToken = token
//                /*AppService.createService(context).let {
//                    apiClient = it
//                }*/
//            })
//        }

    }

    @JvmName("getApiClient1")
    fun getApiClient(): ApiCallInterface {
        if (apiClient == null) {
            //getDeviceToken()
        }
        return apiClient!!
    }


    override fun onCreate() {
        super.onCreate()
        context = this
        myInstance = this
        //getDeviceToken()
        //AppEventsLogger.activateApp(this)
        MultiDex.install(this)
        RetrofitClient.getApiClient(this)?.let { apiClient = it }


    }












    /*fun getDeviceToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            val token = task.result
            Log.d("token123",token.toString())
            AppConstant.DefaultValues.DeviceToken = token
            *//*AppService.createService(context).let {
                apiClient = it
            }*//*
        })
    }

    fun getApiClient(): ApiCallInterface {
        if (apiClient == null) {
            getDeviceToken()
        }
        return apiClient
    }*/


}