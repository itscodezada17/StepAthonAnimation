package com.ujjwal.stepathonanimation.onboarding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ujjwal.stepathonanimation.databinding.ActivityOnboardingBinding

class OnboardingViewPagerAdapter: RecyclerView.Adapter<OnboardingViewPagerAdapter.ViewHolder>() {
    class ViewHolder(val binding: ActivityOnboardingBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ActivityOnboardingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.apply {
            }
        }
    }

    override fun getItemCount() = 3
}