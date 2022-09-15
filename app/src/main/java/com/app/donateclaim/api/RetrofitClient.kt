package com.app.donateclaim.api

import android.content.Context
import com.app.donateclaim.constant.WebConstant
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * This class is used for api calling request handling
 */
object RetrofitClient {

    private val REQUEST_TIMEOUT = 30L
    private var apiInterface: ApiCallInterface? = null
    private var httpClient: OkHttpClient? = null

    fun getApiClient(context: Context): ApiCallInterface? {
        if (httpClient == null) {
            initOkHttp(context)
        }
        if (apiInterface == null) {
            apiInterface = Retrofit.Builder()
                .baseUrl(WebConstant.SERVER_BASE_URL)
                .client(httpClient!!)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync()) // Using create async means all api calls are automatically created asynchronously using OkHttp's thread pool
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().setLenient().create()
                    )
                )
                .build()
                .create(ApiCallInterface::class.java)
        }

        return apiInterface
    }

    private fun initOkHttp(context: Context){

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val networkConnectionInterceptor = NetworkConnectionInterceptor(context)

/*
        val manager = context.packageManager
        val info = manager.getPackageInfo(context.packageName, PackageManager.GET_ACTIVITIES)
        val versionCode: String
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            versionCode = info.longVersionCode.toString()
        } else {
            versionCode = info.versionCode.toString()
        }
*/
        val httpBuilder = OkHttpClient().newBuilder()

            .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(networkConnectionInterceptor)
            .addInterceptor { chain ->

                val original = chain.request()
                val request = original.newBuilder()
                    .addHeader(
                        WebConstant.HeaderKey.KeyUserAgent,
                        WebConstant.HeaderKey.ValueUserAgent,

                    )
                    .addHeader(
                        WebConstant.HeaderKey.KeyContentType,
                        WebConstant.HeaderKey.ValueContentType

                    )
                    .addHeader(
                        WebConstant.HeaderKey.KeySecretKey,
                        WebConstant.HeaderKey.ValueSecretKeyDefault
                    )
                    .addHeader(
                        WebConstant.HeaderKey.IsTestData,
                        WebConstant.HeaderKey.ValueIsTestData
                    )
                    .addHeader(
                        WebConstant.HeaderKey.KeyaccessKey,
                        WebConstant.HeaderKey.ValueaccessKey
                    )

                chain.proceed(request.build())
            }

        httpClient = httpBuilder.build()
    }

}

