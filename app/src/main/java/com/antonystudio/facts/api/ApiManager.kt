package com.antonystudio.facts.api

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ApiManager private constructor(private val apiClient: ApiInterface) : ApiRequest {
    private val DELAY_SUBSCRIPTION_MILLIS: Long = 700L
    companion object {
        @Volatile
        private var instance: ApiManager? = null

        @Synchronized
        fun getApiRequest(apiClient: ApiInterface): ApiManager =
            instance ?: synchronized(this) { ApiManager(apiClient).also { instance = it } }
    }

    override fun getFacts(): Single<ApiResponse> =
        apiClient.getFacts().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .delaySubscription(DELAY_SUBSCRIPTION_MILLIS, TimeUnit.MILLISECONDS)
}