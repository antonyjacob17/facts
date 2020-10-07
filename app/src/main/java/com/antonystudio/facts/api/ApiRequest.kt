package com.antonystudio.facts.api

import io.reactivex.Single
import com.antonystudio.facts.api.ApiResponse

interface ApiRequest {
    fun getFacts(): Single<ApiResponse>
}