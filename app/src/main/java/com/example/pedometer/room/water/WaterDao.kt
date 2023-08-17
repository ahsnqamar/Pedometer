package com.example.pedometer.room.water

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pedometer.models.AddWaterModel

@Dao
interface WaterDao {

    @Insert
    fun insert(water: AddWaterModel)

    @Update
    fun update(water: AddWaterModel)

    @Query("DELETE FROM water_table")
    fun deleteAll()

    @Query("SELECT COUNT(*) FROM water_table")
    fun getCount(): Int

    @Query("UPDATE water_table SET capacityUnits = :units")
    fun updateValues(units: String)

    @Query("SELECT waterValue FROM water_table")
    fun getWaterValues(): List<String>

    @Query("SELECT * FROM water_table")
    fun getWater(): List<AddWaterModel>

    // SELECT USING DATE
    @Query("SELECT * FROM water_table WHERE date = :date")
    fun getWaterByDate(date: String): LiveData<List<AddWaterModel>>

}