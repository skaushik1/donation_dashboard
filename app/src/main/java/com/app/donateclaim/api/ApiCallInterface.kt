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

    @POST(WebConstant.claimProduct)
    fun claimProductApi(
        @Body requestData: JsonObject
    ): Single<BaseResponse>

    @Multipart
    @POST(WebConstant.addProducts)
    fun createProduct(
        @Part("user_id") user_id: RequestBody,
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part("quantity") quantity: RequestBody,
        @Part("name") name: RequestBody,
        @Part("email_id") email_id: RequestBody,
        @Part("phone_number") phone_number: RequestBody,
        @Part product_images: MutableList<MultipartBody.Part>,


    ): Single<BaseResponse>

    @POST(WebConstant.disableProduct)
    fun disablePost(
        @Body requestData: JsonObject
    ): Single<BaseResponse>






















}