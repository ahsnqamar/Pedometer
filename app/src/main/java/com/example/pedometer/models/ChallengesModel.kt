package com.example.pedometer.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "challenges_table")
data class ChallengesModel(

    val name: String,
    val challengeSteps: String,
    val challengeDays: String,
    val challengeType: String,
    val challengeStatus: String,
    val challengeProgress: String ?= null,
    val challengeProgressSteps: String ?= null,
    val challengeProgressDays: String ?= null,
    val uniqueId: String ?= null,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    ) : Serializable {

    constructor(
        name: String,
        challengeSteps: String,
        challengeDays: String,
        challengeType: String,
        challengeStatus: String,
    ) : this(
        name,
        challengeSteps,
        challengeDays,
        challengeType,
        challengeStatus,
        ""
    )
    constructor(): this(
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        null
    )

}