package com.app.donateclaim.Ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.app.donateclaim.BaseViewModel
import com.app.donateclaim.constant.WebConstant
import com.app.donateclaim.model.ProductDetailsResponse
import com.taxibookingapp.base.rxjava.autoDispose
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProductDetailsViewModel : BaseViewModel() {

    val TAG = GetProductListViewModelClass::class.simpleName
    var ProductDetailsResponse: MutableLiveData<ProductDetailsResponse> = MutableLiveData()

    /**
     * This method is used to call GetProduct Api
     */
    fun ProductDetails(
        Id: String,
        type:String
    ) {
        //Api call for get ServicesType and CarTypes
        apiInterface.productDetails(
            requestData = WebConstant.ApiRequestData.getAllProductDetails(
                 id = Id,
                type=type
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
            .subscribe({ getAllProductResponse ->
                if(getAllProductResponse.media.isNotEmpty()){
                    this.ProductDetailsResponse.postValue(
                        ProductDetailsResponse(
                            message = getAllProductResponse.message,
                            status = getAllProductResponse.status,
                            media = getAllProductResponse.media
                            //loadMore = getAllNotificationResponse.loadMore
                        )
                    )
                }
                else {
                    this.ProductDetailsResponse.postValue(
                        ProductDetailsResponse(
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