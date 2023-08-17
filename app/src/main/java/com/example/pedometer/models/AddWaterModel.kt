package com.example.pedometer.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "water_table")
data class AddWaterModel(val waterImage: Int, val waterValue: String, val date: String,val capacityUnits: String, @PrimaryKey(autoGenerate = false) val id: Int ?= null)