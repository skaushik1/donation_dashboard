package com.app.donateclaim.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.donateclaim.MyApplication
import com.app.donateclaim.api.ApiCallInterface
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val isLoading :MutableLiveData<Boolean> = MutableLiveData()
    val message: MutableLiveData<String> = MutableLiveData()
    val networkConnectionMessage: MutableLiveData<Boolean> = MutableLiveData()
    protected val apiInterface: ApiCallInterface = MyApplication.Companion.getInstance().getApiClient()

    init {
        apiInterface.apply {
        }
    }
}