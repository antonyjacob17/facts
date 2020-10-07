package com.antonystudio.facts.api

import io.reactivex.Single
import retrofit2.http.GET

interface ApiInterface {

    @GET(ApiProvider.ApiFacts)
    fun getFacts(): Single<ApiResponse>
}