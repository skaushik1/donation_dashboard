package com.app.donateclaim.api

import com.app.donateclaim.constant.WebConstant
import com.app.donateclaim.model.BaseResponse
import com.app.donateclaim.model.GetProductResponse
import com.app.donateclaim.model.ProductDetailsResponse
import com.app.donateclaim.model.RegisterDevicesResponse
import com.google.gson.JsonObject
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * This class contains all the api calls
 */
interface ApiCallInterface {

    @POST(WebConstant.Register)
    fun login(
        @Body requestData: JsonObject
    ): Single<RegisterDevicesResponse>


    @POST(WebConstant.GetProductList)
    fun getProductList(
        @Body requestData: JsonObject
    ): Single<GetProductResponse>


    @POST(WebConstant.ProductDetails)
    fun productDetails(
        @Body requestData: JsonObject
    ): Single<ProductDetailsResponse>

//    @Multipart
//    @POST(WebConstant.Register)
//    fun register(
//        @Part("firstName") firstName: RequestBody,
//        @Part("lastName") lastName: RequestBody,
//        @Part("email") email: RequestBody,
//        @Part("password") password: RequestBody,
//        @Part("role") role: RequestBody,
//        @Part("deviceType") deviceType: RequestBody,
//        @Part("deviceToken") deviceToken: RequestBody,
//        @Part("gender") gender: RequestBody,
//        @Part("phone") phone: RequestBody,
//        @Part("countryCode") countryCode: RequestBody,
//    ): Single<UserResponse>






















}