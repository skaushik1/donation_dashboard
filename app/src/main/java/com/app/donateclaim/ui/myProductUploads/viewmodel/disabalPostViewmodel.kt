package com.app.donateclaim.Ui.myProductUploads.viewmodel

import androidx.lifecycle.MutableLiveData
import com.app.donateclaim.base.BaseViewModel
import com.app.donateclaim.constant.WebConstant
import com.app.donateclaim.model.BaseResponse
import com.app.donateclaim.model.RegisterDevicesResponse
import com.taxibookingapp.base.rxjava.autoDispose
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DisabalPostViewmodel : BaseViewModel() {

    val TAG = DisabalPostViewmodel::class.simpleName
    var disablepost: MutableLiveData<BaseResponse> = MutableLiveData()

    /**
     * This method is used to call login api
     */
    fun disabalPost(
        id: String,
        is_disable:String
    ) {
        //Api call for registerDevices
        apiInterface.disablePost(
            requestData = WebConstant.ApiRequestData.disablePost(
                id = id,
                is_disable=is_disable
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

                    this.disablepost.postValue(
                        BaseResponse(
                            message = responseHandler.message,
                            status = responseHandler.status,
                        )
                    )

                } else {
                    this.disablepost.postValue(
                        BaseResponse(
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