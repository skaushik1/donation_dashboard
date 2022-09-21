package com.app.donateclaim.Ui.myProductUploads.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.donateclaim.base.BaseViewModel
import com.app.donateclaim.constant.AppConstant
import com.app.donateclaim.model.BaseResponse
import com.taxibookingapp.base.rxjava.autoDispose
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class ProductUploadViewModel : BaseViewModel() {

    val TAG = ProductUploadViewModel::class.simpleName.toString()
    var createProductResponse: MutableLiveData<BaseResponse> = MutableLiveData()

    /**

     * This method is used to perform api call for user update profile
     */
    fun callCreateProductApi(
        userId: String,
        title: String,
        description: String,
        quantity: String,
        name: String,
        email_id:String,
        phone_number:String,
        product_images: MutableList<String>,
    ) {
        var keyImagePart: MutableList<MultipartBody.Part> = mutableListOf()

        for (index in 0 until product_images.size) {
            val file: File = File(product_images.get(index))
            val pics: RequestBody =
                file.asRequestBody(AppConstant.ImageTypes.ImageType.toMediaType())
            keyImagePart.add(
                MultipartBody.Part.createFormData(
                    "product_images[]",
                    file.name,
                    pics
                )
            )
        }

        apiInterface.createProduct(
            user_id = userId.toRequestBody(),
            title = title.toRequestBody(),
            description = description.toRequestBody(),
            quantity = quantity.toRequestBody(),
            name=name.toRequestBody(),
            email_id=email_id.toRequestBody(),
            phone_number=phone_number.toRequestBody(),
            product_images = keyImagePart,


            ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                isLoading.postValue(true)
            }
            .doOnDispose { isLoading.postValue(false) }
            .doOnSuccess { isLoading.postValue(false) }
            .doOnError {
                isLoading.postValue(false)
                it.printStackTrace()
            }
            .subscribe({ createPostResponse ->
                if (createPostResponse.status == "1") {
                    this.createProductResponse.postValue(
                        BaseResponse(
                            message = createPostResponse.message,
                            status = createPostResponse.status,
                            )
                    )
                } else {
                    Log.d("error", createPostResponse.message)
                    this.createProductResponse.postValue(
                        BaseResponse(
                            message = createPostResponse.message,
                            status = createPostResponse.status,

                            )
                    )
                }
                isLoading.postValue(false)
            }, {
                it.printStackTrace()
            }).autoDispose(compositeDisposable = compositeDisposable)

    }
}