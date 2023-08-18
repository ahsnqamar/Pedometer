package com.example.pedometer.utils

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.pedometer.models.StepCountModel
import com.example.pedometer.room.steps.StepsDB
import com.example.pedometer.sharedPreferences.SharedPrefs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StepsManager (context: Context) {
    private val sharedPrefs = SharedPrefs(context)

    private fun populateStepsDB(db: StepsDB) {
        val stepsDao = db.stepsDao()
        val steps = sharedPrefs.getSteps()
        val distance = sharedPrefs.getDistance()
        val calories = sharedPrefs.getCalories()
        val time = sharedPrefs.getTime()
        val date = getTodayDate()
        val hour = getHour()

        stepsDao.insert(StepCountModel(steps, distance, calories, time, hour, date))
    }

    fun insertIntoStepsDB(context: Context) {
        val database = StepsDB.getInstance(context)
        val stepsDao = database.stepsDao()
        CoroutineScope(Dispatchers.IO).launch {
            populateStepsDB(database)
            val data = stepsDao.getAllSteps()
            println("steps inserted after hour: $data")
        }
    }

    private fun getTodayDate(): String {
        val calendar = java.util.Calendar.getInstance()
        val date = calendar.get(java.util.Calendar.DATE)
        val month = calendar.get(java.util.Calendar.MONTH)
        val year = calendar.get(java.util.Calendar.YEAR)
        val today = "$date/$month/$year"
        println("today: $today")
        return today
    }

    private fun getHour() : Long {
        val calendar = java.util.Calendar.getInstance()
        val hour = calendar.get(java.util.Calendar.HOUR_OF_DAY)
        println("hour: $hour")
        return hour.toLong()
    }



}