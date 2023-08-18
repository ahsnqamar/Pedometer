package com.example.pedometer.room.steps

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pedometer.models.StepCountModel

@Dao
interface StepsDao {

    @Insert
    fun insert(steps: StepCountModel)

    @Update
    fun update(steps: StepCountModel)

    @Delete
    fun delete(steps: StepCountModel)

    @Query("DELETE FROM steps_table")
    fun deleteAll()

    @Query("SELECT * FROM steps_table")
    fun getAllSteps(): List<StepCountModel>

    @Query("SELECT * FROM steps_table WHERE date = :date")
    fun getStepsByDate(date: String): List<StepCountModel>

    @Query("SELECT * FROM steps_table WHERE timeStamp = :timeStamp")
    fun getStepsByTimeStamp(timeStamp: Long): List<StepCountModel>

}