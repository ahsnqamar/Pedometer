package com.example.pedometer.models

data class HistoryModel(
    val id: Int? = null,
    var startDate: Long,
    var endDate: Long,
    var totalSteps: Int,
    val historyData: MutableList<HistoryDataModel>
)
