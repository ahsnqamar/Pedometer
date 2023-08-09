package com.example.pedometer.models

data class HistoryDataModel(
    val id: Int? = null,
    var date: Long,
    var userWalkTime: Long,
    var steps: Int,
    var calories: Double,
    var distance: Double,
    var isChecked : Boolean = false
)
