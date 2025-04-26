package com.aos_kotlin.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aos_kotlin.fragment.MonthlyStatisticsFragment
import com.aos_kotlin.fragment.WeeklyStatisticsFragment

class StatisticsPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> WeeklyStatisticsFragment()
            else -> MonthlyStatisticsFragment()
        }
    }
} 