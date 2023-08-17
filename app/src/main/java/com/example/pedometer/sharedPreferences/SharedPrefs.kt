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


    // water data below -------------------------------------------------------------------------------------------------------------------------- //

    private val waterPrefsName = "WaterPrefs"
    private val waterPrefs = context.getSharedPreferences(waterPrefsName, Context.MODE_PRIVATE)
    private val waterEditor = waterPrefs.edit()

    fun getCupSize(): Int {
        return waterPrefs.getInt("cupSize", 0)
    }

    fun setCupSize(cupSize: Int) {
        waterEditor.putInt("cupSize", cupSize)
        waterEditor.apply()
    }

    fun saveWaterGoal(goal: Int) {
        waterEditor.putInt("userWaterGoal", goal)
        waterEditor.apply()
    }

    fun getWaterGoal(): Int {
        return waterPrefs.getInt("userWaterGoal", 0)
    }

    fun getWaterUnits(): String {
        return waterPrefs.getString("waterUnits", "ml")!!
    }

    fun setWaterUnits(units: String) {
        waterEditor.putString("waterUnits", units)
        waterEditor.apply()
    }

    fun setTrackWater(trackWater: Boolean) {
        waterEditor.putBoolean("trackWater", trackWater)
        waterEditor.apply()
    }

    fun getTrackWater(): Boolean {
        return waterPrefs.getBoolean("trackWater", false)
    }

    fun setCurrentWaterIntake(currentWaterIntake: Int){
        waterEditor.putInt("currentWaterIntake", currentWaterIntake)
        waterEditor.apply()
    }

    fun getCurrentWaterIntake(): Int{
        return waterPrefs.getInt("currentWaterIntake", 0)
    }

    fun setUnitsChanged(unitsChanged: Boolean){
        waterEditor.putBoolean("unitsChanged", unitsChanged)
        waterEditor.apply()
    }

    fun getUnitsChanged(): Boolean{
        return waterPrefs.getBoolean("unitsChanged", false)
    }






}