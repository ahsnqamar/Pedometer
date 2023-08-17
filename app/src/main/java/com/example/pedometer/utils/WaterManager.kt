package com.example.pedometer.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.example.pedometer.R
import com.example.pedometer.bottomsheets.WaterIntakeSheet
import com.example.pedometer.models.AddWaterModel
import com.example.pedometer.room.water.WaterDB
import com.example.pedometer.sharedPreferences.SharedPrefs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class WaterManager(context: Context) {

    private val sharedPrefs = SharedPrefs(context)



    companion object {
        var currentWaterIntake: WaterManager.CurrentWaterIntake? = null
    }

    private fun getTodayDate(): String{
        val calendar = Calendar.getInstance()
        val date = calendar.get(Calendar.DATE)
        println("date: $date")
        return date.toString()
    }

    private fun populateWaterDB(db: WaterDB){
        val waterDao = db.waterDao()
        val value = sharedPrefs.getCupSize()
        waterDao.insert(AddWaterModel(R.drawable.water_glass, "${sharedPrefs.getCupSize()}", getTodayDate(), sharedPrefs.getWaterUnits()))
    }

    fun updateWaterValues(context: Context, units: String){
        val database = WaterDB.getInstance(context)
        val waterDao = database.waterDao()
        CoroutineScope(Dispatchers.IO).launch {
            waterDao.updateValues(units)
        }
    }

    fun calculateWaterIntake(context: Context){
        val totalIntake = sharedPrefs.getCupSize()
        var totalCount = 0
        val waterDao = WaterDB.getInstance(context).waterDao()
        CoroutineScope(Dispatchers.IO).launch {
            totalCount = waterDao.getCount()
            println("totalCount: $totalCount")
            println("totalIntake: $totalIntake")
            //sharedPrefs.setCurrentWaterIntake(totalCount * totalIntake)
        }
    }

    fun getWaterValues(context: Context): List<String>{
        val database = WaterDB.getInstance(context)
        val waterDao = database.waterDao()
        var waterValues: List<String> = emptyList()
        CoroutineScope(Dispatchers.IO).launch {
            waterValues = waterDao.getWaterValues()
            waterValues.forEach { _ ->
                // add all values
                val sum = waterValues.sumOf { it.toInt() }
                println("sum: $sum")
                withContext(Dispatchers.Main){
                    currentWaterIntake?.onCurrentWaterIntakeChanged(sum)
                }
                sharedPrefs.setCurrentWaterIntake(sum)
            }
            println("waterValues: $waterValues")
        }
        return waterValues
    }

    fun insertIntoWaterDB(context: Context){
        val database = WaterDB.getInstance(context)
        val waterDao = database.waterDao()
        CoroutineScope(Dispatchers.IO).launch {
            calculateWaterIntake(context)
            populateWaterDB(database)
            getWaterValues(context)
            val dataInserted = waterDao.getWater()
            println("dataInserted: $dataInserted")
        }
    }
    fun getWaterData(context: Context): LiveData<List<AddWaterModel>>{
        val database = WaterDB.getInstance(context)
        val waterDao = database.waterDao()
        val date = getTodayDate()
        //getWaterValues(context)
/*        if (sharedPrefs.getUnitsChanged()){
            println("units changed 2")
            getWaterValues(context).forEach {
                if (sharedPrefs.getWaterUnits() == "fl oz"){
                    val ml = ouncesToMl(it.toInt())
                    println("ml: $ml")
                    updateWaterValues(context, sharedPrefs.getWaterUnits(), ml.toString())
                }
                else{
                    val ounces = mlToOunces(it.toInt())
                    println("ounces: $ounces")
                    updateWaterValues(context, sharedPrefs.getWaterUnits(), ounces.toString())
                }
            }
        }*/
        return waterDao.getWaterByDate(date)
    }



    fun deleteAllWaterData(context: Context){
        val database = WaterDB.getInstance(context)
        val waterDao = database.waterDao()
        CoroutineScope(Dispatchers.IO).launch {
            waterDao.deleteAll()
        }
    }

    interface CurrentWaterIntake {
        fun onCurrentWaterIntakeChanged(currentWaterIntake: Int)
    }

    private val timeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == Intent.ACTION_DATE_CHANGED){
                println("date changed")
                deleteAllWaterData(context!!)
                //insertIntoWaterDB(context)
            }
        }
    }

    fun registerTimeReceiver(context: Context){
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_DATE_CHANGED)
        context.registerReceiver(timeReceiver, intentFilter)
    }

}