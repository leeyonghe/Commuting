package com.aos_kotlin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.aos_kotlin.R
import com.aos_kotlin.database.AppDatabase
import com.aos_kotlin.databinding.FragmentWeeklyStatisticsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class WeeklyStatisticsFragment : Fragment() {
    private var _binding: FragmentWeeklyStatisticsBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: AppDatabase
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeeklyStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = AppDatabase.getDatabase(requireContext())
        loadWeeklyStatistics()
    }

    private fun loadWeeklyStatistics() {
        lifecycleScope.launch(Dispatchers.IO) {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
            val startDate = calendar.time
            calendar.add(Calendar.DAY_OF_WEEK, 6)
            val endDate = calendar.time

            val workRecords = database.workRecordDao().getWorkRecordsByDateRange(startDate, endDate)
            workRecords.collect { records ->
                val totalWorkHours = records.sumOf { it.workDuration ?: 0 } / 60.0
                val workDays = records.count { it.endTime != null }

                activity?.runOnUiThread {
                    binding.tvTotalWorkHours.text = String.format("%.1f시간", totalWorkHours)
                    binding.tvWorkDays.text = getString(R.string.work_days_format, workDays)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 