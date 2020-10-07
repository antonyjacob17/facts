package com.antonystudio.facts.datamanager

import androidx.lifecycle.LiveData
import com.antonystudio.facts.api.ApiRequest
import com.antonystudio.facts.model.Facts

interface DataManager:ApiRequest {

    fun getFactsList(): LiveData<MutableList<Facts>>
    fun insertAllData(facts: List<Facts>)
    fun clearData()
}