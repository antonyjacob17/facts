package com.antonystudio.facts

import android.app.Application
import com.antonystudio.facts.datamanager.AppManager
import com.antonystudio.facts.datamanager.DataManager

class FactsApplication : Application() {
    private val dataManager: DataManager by lazy { AppManager.getDataManager(this) }

    companion object {
        private lateinit var instance: FactsApplication
        fun getApiClient(): DataManager = instance.dataManager
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
    }
}