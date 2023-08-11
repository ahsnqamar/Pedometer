package com.example.pedometer.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pedometer.R
import com.example.pedometer.databinding.OngoingChallengesLayoutBinding
import com.example.pedometer.models.ChallengesModel

class ChallengesAdapter: RecyclerView.Adapter<ChallengesAdapter.ViewHolder>(){

    private val mAllChallenges: MutableList<ChallengesModel> = mutableListOf()

    class ViewHolder(val binding: OngoingChallengesLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(OngoingChallengesLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return mAllChallenges.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val challengesModel = mAllChallenges[position]
        with(holder.binding){
            mainTextChallengeCard.text = challengesModel.name
            topTxtChallengeCard.text = challengesModel.challengeSteps
            btmDaysTxtChallengeCard.text = challengesModel.challengeDays

            if (challengesModel.challengeType == "beginners") {
                progressChallengeCard.setBackgroundResource(R.drawable.steps_bg_small)
                btmDaysTxtChallengeCard.setBackgroundResource(R.drawable.green_bg_challenge)
            } else {
                progressChallengeCard.setBackgroundResource(R.drawable.calories_bg_small)
                btmDaysTxtChallengeCard.setBackgroundColor(btmDaysTxtChallengeCard.context.resources.getColor(R.color.caloriesColor))
            }

            progressTxtChallengeCard.text = challengesModel.challengeProgress
            progressChallengeCard.progress = challengesModel.challengeProgress.toInt()
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(challengesList: List<ChallengesModel>){
        mAllChallenges.clear()
        mAllChallenges.addAll(challengesList)
        notifyDataSetChanged()
    }

}