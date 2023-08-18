package com.example.pedometer.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "steps_table")
data class StepCountModel (
    var steps: Int,
    var distance: Float,
    var calories: Float,
    var time: Int,
    var timeStamp: Long ?= null,
    var date: String ?= null,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
) {
    constructor() : this(0, 0f, 0f, 0)
}