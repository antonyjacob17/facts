package com.antonystudio.facts.ui.base

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.antonystudio.facts.api.NetworkState
import com.antonystudio.facts.utility.NetworkProvider
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    protected val disposables: CompositeDisposable by lazy { CompositeDisposable() }

    protected val loaderObservable: MutableLiveData<NetworkState> by lazy {
        MutableLiveData<NetworkState>()
    }

    //Method for checking the network availability
    protected fun checkNetworkAvailability(context: Context): Boolean {
        if (!NetworkProvider.isConnected(context)) {
            loaderObservable.value = NetworkState.NetworkNotAvailable
            return false
        }
        return true
    }

    override fun onCleared() {
        if ((disposables.size() > 0) && !disposables.isDisposed) {
            disposables.dispose()
            disposables.clear()
        }
        super.onCleared()
    }
}