package com.example.pedometer.room.challenges

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pedometer.models.ChallengesModel

@Dao
interface ChallengesDao {

    @Insert
    fun insert(challenges: ChallengesModel)

    @Update
    fun update(challenges: ChallengesModel)

    @Query("SELECT * FROM challenges_table")
    fun getAllChallenges(): List<ChallengesModel>

    @Query("UPDATE challenges_table SET challengeProgress = :progress WHERE uniqueId = :uniqueId")
    fun updateProgress(progress: String,  uniqueId: String)

}