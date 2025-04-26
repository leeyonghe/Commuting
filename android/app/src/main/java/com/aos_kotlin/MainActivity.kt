package com.aos_kotlin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aos_kotlin.database.AppDatabase
import com.aos_kotlin.databinding.ActivityMainBinding
import com.aos_kotlin.model.WorkRecord
import com.aos_kotlin.preference.WorkPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: AppDatabase
    private lateinit var workPreference: WorkPreference
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)
        workPreference = WorkPreference(this)

        updateCurrentTime()
        setupClickListeners()
    }

    private fun updateCurrentTime() {
        val currentTime = Date()
        binding.tvCurrentTime.text = timeFormat.format(currentTime)
    }

    private fun setupClickListeners() {
        binding.btnStartTime.setOnClickListener {
            recordStartTime()
        }

        binding.btnEndTime.setOnClickListener {
            recordEndTime()
        }

        binding.btnStatistics.setOnClickListener {
            startActivity(android.content.Intent(this, StatisticsActivity::class.java))
        }

        binding.btnSettings.setOnClickListener {
            startActivity(android.content.Intent(this, SettingsActivity::class.java))
        }
    }

    private fun recordStartTime() {
        val today = dateFormat.format(Date())
        val currentTime = Date()

        lifecycleScope.launch(Dispatchers.IO) {
            val existingRecord = database.workRecordDao().getWorkRecordByDate(dateFormat.parse(today)!!)
            if (existingRecord != null) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "이미 출근 기록이 있습니다.", Toast.LENGTH_SHORT).show()
                }
                return@launch
            }

            val workRecord = WorkRecord(
                date = dateFormat.parse(today)!!,
                startTime = currentTime
            )
            database.workRecordDao().insertWorkRecord(workRecord)
            runOnUiThread {
                Toast.makeText(this@MainActivity, "출근 시간이 기록되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun recordEndTime() {
        val today = dateFormat.format(Date())
        val currentTime = Date()

        lifecycleScope.launch(Dispatchers.IO) {
            val workRecord = database.workRecordDao().getWorkRecordByDate(dateFormat.parse(today)!!)
            if (workRecord == null) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "출근 기록이 없습니다.", Toast.LENGTH_SHORT).show()
                }
                return@launch
            }

            if (workRecord.endTime != null) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "이미 퇴근 기록이 있습니다.", Toast.LENGTH_SHORT).show()
                }
                return@launch
            }

            val workDuration = ((currentTime.time - workRecord.startTime.time) / (1000 * 60)).toInt()
            val updatedRecord = workRecord.copy(
                endTime = currentTime,
                workDuration = workDuration
            )
            database.workRecordDao().updateWorkRecord(updatedRecord)

            if (workPreference.isOvertimeNotificationEnabled) {
                val standardMinutes = workPreference.standardWorkHours * 60 + workPreference.standardWorkMinutes
                if (workDuration > standardMinutes) {
                    val overtimeMinutes = workDuration - standardMinutes
                    runOnUiThread {
                        Toast.makeText(
                            this@MainActivity,
                            "초과 근무 시간: ${overtimeMinutes / 60}시간 ${overtimeMinutes % 60}분",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

            runOnUiThread {
                Toast.makeText(this@MainActivity, "퇴근 시간이 기록되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
} 