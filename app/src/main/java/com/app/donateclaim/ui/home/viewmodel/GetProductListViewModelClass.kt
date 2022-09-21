package com.app.donateclaim.Ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.app.donateclaim.base.BaseViewModel
import com.app.donateclaim.constant.WebConstant
import com.app.donateclaim.model.GetProductResponse
import com.taxibookingapp.base.rxjava.autoDispose
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetProductListViewModelClass : BaseViewModel() {

    val TAG = GetProductListViewModelClass::class.simpleName
    var getAllProductResponse: MutableLiveData<GetProductResponse> = MutableLiveData()

    /**
     * This method is used to call GetProduct Api
     */
    fun getAllProduct(
        user_id:String,
        type:String,
    ) {
        //Api call for get ServicesType and CarTypes
        apiInterface.getProductList(
            requestData = WebConstant.ApiRequestData.getAllProduct(user_id = user_id,
            type = type)
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
            .subscribe({ getAllProductResponse ->
                if(getAllProductResponse.data != null){
                    this.getAllProductResponse.postValue(
                        GetProductResponse(
                            message = getAllProductResponse.message,
                            status = getAllProductResponse.status,
                            data = getAllProductResponse.data
                            //loadMore = getAllNotificationResponse.loadMore
                        )
                    )
                }
                else {
                    this.getAllProductResponse.postValue(
                        GetProductResponse(
                            message = getAllProductResponse.message,
                            status = getAllProductResponse.status,
                        )
                    )
                }
            }, {
                it.printStackTrace()
            }).autoDispose(compositeDisposable = compositeDisposable)
    }
}