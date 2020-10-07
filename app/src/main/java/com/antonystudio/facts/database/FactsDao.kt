package com.antonystudio.facts.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.antonystudio.facts.database.constants.Tables
import com.antonystudio.facts.model.Facts

@Dao
interface FactsDao {

    @Query("SELECT * FROM ${Tables.TABLE_FACTS}")
    fun getAllFacts(): LiveData<MutableList<Facts>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(callData: List<Facts>)

    @Query("DELETE FROM ${Tables.TABLE_FACTS}")
    fun clearAllData()
}