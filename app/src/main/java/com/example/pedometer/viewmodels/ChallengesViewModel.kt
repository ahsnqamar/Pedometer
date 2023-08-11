package com.example.pedometer.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pedometer.models.ChallengesModel

class ChallengesViewModel : ViewModel() {
    private val _challengesModel = MutableLiveData<ChallengesModel>()
    val challengesModel: LiveData<ChallengesModel> = _challengesModel

    fun setChallengesModel(challengesModel: ChallengesModel) {
        _challengesModel.value = challengesModel
    }
}