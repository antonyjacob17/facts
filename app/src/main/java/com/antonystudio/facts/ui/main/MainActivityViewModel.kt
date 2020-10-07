package com.antonystudio.facts.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.antonystudio.facts.api.ApiRequest
import com.antonystudio.facts.datamanager.DataManager
import com.antonystudio.facts.model.Facts
import com.antonystudio.facts.api.NetworkState
import com.antonystudio.facts.ui.base.BaseViewModel
import com.antonystudio.facts.utility.io
import com.antonystudio.facts.extensions.autoDispose
import kotlinx.coroutines.launch

class MainActivityViewModel(private val dataManager: DataManager) : BaseViewModel() {

    companion object {

        @Synchronized
        fun getInstance(dataManager: DataManager): MainActivityViewModel = synchronized(this) {
            MainActivityViewModel(dataManager)
        }
    }

    var factsList: LiveData<MutableList<Facts>> =
        dataManager.getFactsList()

    var title: String = ""

    fun getFacts(): LiveData<MutableList<Facts>> = factsList

    fun getAppBarTitle() = title

    fun getDataStream(): LiveData<NetworkState> = loaderObservable

    //Method for fetching the data from Api
    fun getFactDetails(context: Context) {
        if (checkNetworkAvailability(context)) {
            dataManager.getFacts().doOnSubscribe {
                loaderObservable.value =
                    NetworkState.LoadingData
            }.subscribe({
                try {
                    viewModelScope.launch {
                        io {
                            val data = it.getFactsData()
                            if (data.isNotEmpty())
                                data[0].mainTitle = it.getTitle()
                            dataManager.clearData()
                            val filteredData =
                                data.filter { it.filterData() }
                            dataManager.insertAllData(filteredData)
                            title = it.getTitle()
                        }
                        loaderObservable.value = NetworkState.SuccessResponse(it)
                    }

                } catch (e: Exception) {
//                    println("$TAG ${e.message}")
                }
            }, {
                loaderObservable.value =
                    NetworkState.ErrorResponse
            }).autoDispose(disposables)
        }
    }
}