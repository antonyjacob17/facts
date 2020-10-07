package com.antonystudio.facts.api

import com.antonystudio.facts.BuildConfig

object ApiProvider {

    private const val BASE_URL: String = BuildConfig.HOST_URL

    private const val GET_FACTS: String = "s/2iodh4vg0eortkl/facts.json"
    const val ApiFacts: String = BASE_URL + GET_FACTS
}