package com.app.donateclaim

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()



    val isLoading :MutableLiveData<Boolean> = MutableLiveData()
    val message: MutableLiveData<String> = MutableLiveData()
    val networkConnectionMessage: MutableLiveData<Boolean> = MutableLiveData()
    //protected val apiInterface: ApiCallInterface = MyApplication.getInstance().getApiClient()

//    init {
//        apiInterface.apply {
//        }
//    }
}