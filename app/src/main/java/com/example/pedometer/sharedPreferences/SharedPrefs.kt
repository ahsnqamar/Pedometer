package com.example.pedometer.sharedPreferences

import android.content.Context

class SharedPrefs(context: Context) {

    private val prefsName = "StepCounterPrefs"
    private val prefs = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
    private val editor = prefs.edit()
    private val stepKey = "userSteps"
    private val caloriesKey = "userCalories"
    private val distanceKey = "userDistance"
    private val timeKey = "userTime"


    fun saveSteps(steps: Int) {
        editor.putInt(stepKey, steps)
        editor.apply()
    }

    fun getSteps(): Int {
        return prefs.getInt(stepKey, 0)
    }

    fun saveCalories(calories: Float) {
        editor.putFloat(caloriesKey, calories)
        editor.apply()
    }

    fun getCalories(): Float {
        return prefs.getFloat(caloriesKey, 0f)
    }

    fun saveDistance(distance: Float) {
        editor.putFloat(distanceKey, distance)
        editor.apply()
    }

    fun getDistance(): Float {
        return prefs.getFloat(distanceKey, 0f)
    }

    fun saveTime(time: Int) {
        editor.putInt(timeKey, time)
        editor.apply()
    }

    fun getTime(): Int {
        return prefs.getInt(timeKey, 0)
    }

    fun getWeight(): Int {
        return prefs.getInt("userWeight", 0)
    }

    fun getHeight(): Int {
        return prefs.getInt("userHeight", 0)
    }

    fun getGender(): String {
        return prefs.getString("userGender", "")!!
    }

    fun setWeight(weight: Int) {
        editor.putInt("userWeight", weight)
        editor.apply()
    }

    fun setHeight(height: Int) {
        editor.putInt("userHeight", height)
        editor.apply()
    }

    fun setGender(gender: String){
        editor.putString("userGender", gender)
        editor.apply()
    }

    fun getIsServiceRunning(): Boolean {
        return prefs.getBoolean("isServiceRunning", true)
    }

    fun setIsServiceRunning(isServiceRunning: Boolean) {
        editor.putBoolean("isServiceRunning", isServiceRunning)
        editor.apply()
    }

    fun getUserGoal(): Int {
        return prefs.getInt("userGoal", 0)
    }

    fun setUserGoal(goal: Int) {
        editor.putInt("userGoal", goal)
        editor.apply()
    }






}