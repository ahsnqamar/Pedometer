package com.example.pedometer.room.water

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pedometer.models.AddWaterModel

@Database(entities = [AddWaterModel::class], version = 1)
abstract class WaterDB : RoomDatabase(){

    abstract fun waterDao(): WaterDao

    companion object {
        private var instance: WaterDB ?= null

        @Synchronized
        fun getInstance(ctx: android.content.Context): WaterDB {
            if (instance == null){
                instance = androidx.room.Room.databaseBuilder(ctx.applicationContext, WaterDB::class.java,
                    "water_database")
                    .build()
            }

            return  instance!!
        }
    }

}