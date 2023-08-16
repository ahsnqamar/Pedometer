package com.example.pedometer.models

data class StepCountModel (
    var steps: Int,
    var distance: Float,
    var calories: Float,
    var time: Int
) {
    constructor() : this(0, 0f, 0f, 0)
}