package com.aos_kotlin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aos_kotlin.database.AppDatabase
import com.aos_kotlin.databinding.ActivityMainBinding
import com.aos_kotlin.model.WorkRecord
import com.aos_kotlin.preference.WorkPreference
import com.aos_kotlin.repository.WorkRecordRepository
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: AppDatabase
    private lateinit var workPreference: WorkPreference
    private lateinit var repository: WorkRecordRepository
    private var workRecord: WorkRecord? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)
        workPreference = WorkPreference(this)
        repository = WorkRecordRepository(database.workRecordDao())

        setupViews()
        loadTodayWorkRecord()
    }

    private fun setupViews() {
        binding.btnStart.setOnClickListener {
            recordStartTime()
        }

        binding.btnEnd.setOnClickListener {
            recordEndTime()
        }

        binding.btnStatistics.setOnClickListener {
            startActivity(android.content.Intent(this, StatisticsActivity::class.java))
        }

        binding.btnSettings.setOnClickListener {
            startActivity(android.content.Intent(this, SettingsActivity::class.java))
        }
    }

    private fun loadTodayWorkRecord() {
        lifecycleScope.launch {
            val today = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS)
            workRecord = repository.getWorkRecordByDate(today)
            updateUI()
        }
    }

    private fun recordStartTime() {
        lifecycleScope.launch {
            val now = LocalDateTime.now()
            val today = now.truncatedTo(ChronoUnit.DAYS)
            
            workRecord = WorkRecord(
                date = today,
                startTime = now,
                endTime = null,
                workDuration = 0
            )
            
            workRecord = repository.createWorkRecord(workRecord!!)
            updateUI()
        }
    }

    private fun recordEndTime() {
        lifecycleScope.launch {
            val now = LocalDateTime.now()
            workRecord?.let { record ->
                val updatedRecord = record.copy(
                    endTime = now,
                    workDuration = ChronoUnit.MINUTES.between(record.startTime, now).toInt()
                )
                
                workRecord = repository.updateWorkRecord(updatedRecord)
                updateUI()
                
                // Check for overtime
                val standardMinutes = workPreference.standardWorkHours * 60 + workPreference.standardWorkMinutes
                if (updatedRecord.workDuration > standardMinutes && workPreference.isOvertimeNotificationEnabled) {
                    val overtimeMinutes = updatedRecord.workDuration - standardMinutes
                    val overtimeHours = overtimeMinutes / 60
                    val remainingMinutes = overtimeMinutes % 60
                    Toast.makeText(
                        this@MainActivity,
                        "초과 근무: ${overtimeHours}시간 ${remainingMinutes}분",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun updateUI() {
        workRecord?.let { record ->
            binding.tvStartTime.text = "출근: ${formatDateTime(record.startTime)}"
            binding.tvEndTime.text = record.endTime?.let { "퇴근: ${formatDateTime(it)}" } ?: "퇴근: -"
            binding.tvWorkDuration.text = "근무 시간: ${formatDuration(record.workDuration)}"
            
            binding.btnStart.isEnabled = false
            binding.btnEnd.isEnabled = record.endTime == null
        } ?: run {
            binding.tvStartTime.text = "출근: -"
            binding.tvEndTime.text = "퇴근: -"
            binding.tvWorkDuration.text = "근무 시간: -"
            
            binding.btnStart.isEnabled = true
            binding.btnEnd.isEnabled = false
        }
    }

    private fun formatDateTime(dateTime: LocalDateTime): String {
        return dateTime.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"))
    }

    private fun formatDuration(minutes: Int): String {
        val hours = minutes / 60
        val mins = minutes % 60
        return String.format("%d시간 %d분", hours, mins)
    }
} 