package com.example.pedometer.room.challenges

import androidx.room.Database
import com.example.pedometer.models.ChallengesModel
import com.example.pedometer.room.steps.StepsDB
import com.example.pedometer.room.steps.StepsDao

@Database(entities = [ChallengesModel::class], version = 1)
abstract class ChallengesDB : androidx.room.RoomDatabase() {
    abstract fun challengesDao(): ChallengesDao
    companion object {
        private var instance: ChallengesDB?= null
        @Synchronized
        fun getInstance(ctx: android.content.Context): ChallengesDB {
            if (instance == null) {
                instance = androidx.room.Room.databaseBuilder(ctx.applicationContext, ChallengesDB::class.java, "challenges_database").build()
            }
            return instance!!
        }
    }
}