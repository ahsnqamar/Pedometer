package com.example.pedometer.sharedPreferences

import android.content.Context

class ChallengesSP(context: Context) {

    private val prefsName = "StepCounterPrefs"
    private val prefs = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
    private val editor = prefs.edit()

    fun setChallengeName(challengeName: String) {
        editor.putString("challengeName", challengeName)
        editor.apply()
    }

    fun getChallengeName(): String? {
        return prefs.getString("challengeName", "")
    }

    fun setChallengeSteps(challengeSteps: String) {
        editor.putString("challengeSteps", challengeSteps)
        editor.apply()
    }

    fun getChallengeSteps(): String? {
        return prefs.getString("challengeSteps", "")
    }

    fun setChallengeDays(challengeDays: String) {
        editor.putString("challengeDays", challengeDays)
        editor.apply()
    }

    fun getChallengeDays(): String? {
        return prefs.getString("challengeDays", "")
    }

    fun setChallengeType(challengeType: String) {
        editor.putString("challengeType", challengeType)
        editor.apply()
    }

    fun getChallengeType(): String? {
        return prefs.getString("challengeType", "")
    }

    fun setChallengeStatus(challengeStatus: String) {
        editor.putString("challengeStatus", challengeStatus)
        editor.apply()
    }

    fun getChallengeStatus(): String? {
        println("challengeStatus: ${prefs.getString("challengeStatus", ChallengeStatus.NOT_STARTED.toString())}")
        return prefs.getString("challengeStatus", ChallengeStatus.NOT_STARTED.toString())
    }

    fun setChallengeProgress(challengeProgress: String) {
        editor.putString("challengeProgress", challengeProgress)
        editor.apply()
    }

    fun getChallengeProgress(): String? {
        return prefs.getString("challengeProgress", "")
    }

    enum class ChallengeStatus {
        NOT_STARTED,
        STARTED,
        COMPLETED
    }



}