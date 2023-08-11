package com.example.pedometer.models

import java.lang.reflect.Constructor

data class ChallengesModel(
    val id: Int ?= null,
    val name: String,
    val challengeSteps: String,
    val challengeDays: String,
    val challengeType: String,
    val challengeStatus: String,
    val challengeProgress: String,

) {
    constructor(
        name: String,
        challengeSteps: String,
        challengeDays: String,
        challengeType: String,
        challengeStatus: String,
    ) : this(null, name, challengeSteps, challengeDays, challengeType, challengeStatus,"")
}

