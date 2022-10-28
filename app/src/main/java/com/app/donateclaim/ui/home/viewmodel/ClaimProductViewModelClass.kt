package com.app.donateclaim.Ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.app.donateclaim.base.BaseViewModel
import com.app.donateclaim.Ui.splesh.viewmodel.RegisterUserViewModelClass
import com.app.donateclaim.constant.WebConstant
import com.app.donateclaim.model.BaseResponse
import com.app.donateclaim.model.RegisterDevicesResponse
import com.taxibookingapp.base.rxjava.autoDispose
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ClaimProductViewModelClass: BaseViewModel() {

    val TAG = ClaimProductViewModelClass::class.simpleName
    var claimProduct: MutableLiveData<BaseResponse> = MutableLiveData()

    /**
     * This method is used to call ClaimProduct Api...
     */
    fun claimProductApi(
        userId: String,
        product_id:String,
        name:String,
        email_id:String,
        phone_number:String,
    ) {
        //Api call for registerDevices

        apiInterface.claimProductApi(
            requestData = WebConstant.ApiRequestData.claimProductRequestBody(
                user_id = userId,
                product_id=product_id,
                name = name,
                email_id=email_id,
                phone_number=phone_number
            )
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                isLoading.postValue(true)
            }
            .doOnDispose { isLoading.postValue(false) }
            .doOnSuccess { isLoading.postValue(false) }
            .doOnError { isLoading.postValue(false)
                it.printStackTrace()
            }
            .subscribe({ responseHandler ->

                if (responseHandler.status == "1") {
                    //perform action on success of login

                    this.claimProduct.postValue(
                        responseHandler.message?.let {
                            BaseResponse(
                                message = it,
                                status = responseHandler.status,

                                )
                        }
                    )

                } else {
                    this.claimProduct.postValue(
                        responseHandler.message?.let {
                            responseHandler.status?.let { it1 ->
                                BaseResponse(
                                    message = it,
                                    status = it1,
                                )
                            }
                        }
                    )
                }
            }, {
                it.printStackTrace()
            }).autoDispose(compositeDisposable = compositeDisposable)
    }
}