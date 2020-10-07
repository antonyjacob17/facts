package com.antonystudio.facts.api

import com.antonystudio.facts.model.Facts
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ApiResponse {
    @SerializedName("title")
    @Expose
    private var mainTitle: String = ""

    @SerializedName("rows")
    @Expose
    private val facts: List<Facts> = mutableListOf()

    fun getFactsData(): List<Facts> = facts

    fun getTitle(): String = mainTitle

}