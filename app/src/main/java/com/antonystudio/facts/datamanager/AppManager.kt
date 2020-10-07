package com.antonystudio.facts.datamanager

import android.app.Application
import androidx.lifecycle.LiveData
import com.antonystudio.facts.api.ApiManager
import com.antonystudio.facts.api.ApiRequest
import com.antonystudio.facts.api.ApiResponse
import com.antonystudio.facts.api.RetroClient
import com.antonystudio.facts.database.FactsDao
import com.antonystudio.facts.database.FactsDatabase
import com.antonystudio.facts.model.Facts
import io.reactivex.Single

class AppManager private constructor(
    private val apiRequest: ApiRequest, private val dbManager: FactsDatabase
) : DataManager {

    //Database calls
    override fun getFactsList(): LiveData<MutableList<Facts>> = factsDao.getAllFacts()
    override fun insertAllData(facts: List<Facts>) = factsDao.insertAll(facts)
    override fun clearData() = factsDao.clearAllData()

    private val factsDao: FactsDao by lazy { dbManager.provideFactsDao() }

    // ApiRequests
    override fun getFacts(): Single<ApiResponse> = apiRequest.getFacts()

    companion object {
        @Volatile
        private var instance: AppManager? = null

        @Synchronized
        fun getDataManager(application: Application): AppManager =
            instance ?: synchronized(this) {
                AppManager(
                    ApiManager.getApiRequest(RetroClient.createApiClient(application)),
                    FactsDatabase.getDatabase(application)
                ).also { instance = it }
            }
    }


}