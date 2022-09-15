package com.app.donateclaim.Ui.splesh.viewmodel

import androidx.lifecycle.MutableLiveData
import com.app.donateclaim.BaseViewModel
import com.app.donateclaim.constant.WebConstant
import com.app.donateclaim.model.BaseResponse
import com.app.donateclaim.model.RegisterDevicesResponse
import com.taxibookingapp.base.rxjava.autoDispose
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RegisterUserViewModelClass : BaseViewModel() {

    val TAG = RegisterUserViewModelClass::class.simpleName
    var registerDevicesViewModel: MutableLiveData<RegisterDevicesResponse> = MutableLiveData()

    /**
     * This method is used to call login api
     */
    fun registerDevicesApi(
        deviceId: String,
    ) {
        //Api call for registerDevices
        apiInterface.login(
            requestData = WebConstant.ApiRequestData.registerDevicesIdRequestBody(
                devicesId = deviceId,
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

                        this.registerDevicesViewModel.postValue(
                            RegisterDevicesResponse(
                                message = responseHandler.message,
                                status = responseHandler.status,
                                userId = responseHandler.userId
                            )
                        )

                } else {
                    this.registerDevicesViewModel.postValue(
                        RegisterDevicesResponse(
                            message = responseHandler.message,
                            status = responseHandler.status,
                        )
                    )
                }
            }, {
                it.printStackTrace()
            }).autoDispose(compositeDisposable = compositeDisposable)
    }
}