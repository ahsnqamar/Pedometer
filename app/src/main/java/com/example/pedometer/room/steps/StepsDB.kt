package com.example.pedometer.room.steps

import androidx.room.Database
import com.example.pedometer.models.StepCountModel


@Database(entities = [StepCountModel::class], version = 1)
abstract class StepsDB : androidx.room.RoomDatabase() {
    abstract fun stepsDao(): StepsDao
    companion object {
        private var instance: StepsDB ?= null
        @Synchronized
        fun getInstance(ctx: android.content.Context): StepsDB {
            if (instance == null) {
                instance = androidx.room.Room.databaseBuilder(ctx.applicationContext, StepsDB::class.java, "steps_database").build()
            }
            return instance!!
        }
    }
}