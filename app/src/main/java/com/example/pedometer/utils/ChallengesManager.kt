package com.example.pedometer.utils

import android.content.Context
import com.example.pedometer.models.ChallengesModel
import com.example.pedometer.room.challenges.ChallengesDB
import com.example.pedometer.sharedPreferences.ChallengesSP
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChallengesManager (context: Context) {

    private var challengesSP = ChallengesSP(context)

/*    private fun populateDB(db: ChallengesDB) {
        val challengesDao = db.challengesDao()
        challengesDao.insert(ChallengesModel("beginners", "Walk 1000 steps a day", "1000", "7", "0"))
    }*/

    fun insertIntoDB(context: Context) {
        val database = ChallengesDB.getInstance(context)
        val challengesDao = database.challengesDao()
        CoroutineScope(Dispatchers.IO).launch {
            //populateDB(database)
            challengesDao.insert(ChallengesModel("${challengesSP.getChallengeName()}", "${challengesSP.getChallengeSteps()}", "${challengesSP.getChallengeDays()}", "${challengesSP.getChallengeType()}", "${challengesSP.getChallengeStatus()}"))
            val data = challengesDao.getAllChallenges()
            println("challenges inserted: $data")
        }
    }



    fun getChallengesList(context: Context): List<ChallengesModel> {
        val database = ChallengesDB.getInstance(context)
        val challengesDao = database.challengesDao()
        challengesDao.getAllChallenges().forEach { challengesModel ->
            println("challengesModel: $challengesModel")
        }
        return challengesDao.getAllChallenges()
    }

    fun updateProgress(context: Context, progress: String,  uniqueId: String) {
        val database = ChallengesDB.getInstance(context)
        val challengesDao = database.challengesDao()
        CoroutineScope(Dispatchers.IO).launch {
            challengesDao.updateProgress(progress, uniqueId)
            println("progress updated")
        }
    }



}