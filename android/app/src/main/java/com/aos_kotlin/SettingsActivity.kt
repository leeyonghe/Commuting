package com.aos_kotlin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aos_kotlin.databinding.ActivitySettingsBinding
import com.aos_kotlin.preference.WorkPreference

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var workPreference: WorkPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        workPreference = WorkPreference(this)
        setupViews()
    }

    private fun setupViews() {
        // Set initial values
        binding.numberPickerHours.minValue = 0
        binding.numberPickerHours.maxValue = 24
        binding.numberPickerHours.value = workPreference.standardWorkHours

        binding.numberPickerMinutes.minValue = 0
        binding.numberPickerMinutes.maxValue = 59
        binding.numberPickerMinutes.value = workPreference.standardWorkMinutes

        binding.switchOvertimeNotification.isChecked = workPreference.isOvertimeNotificationEnabled

        // Save button click listener
        binding.btnSaveSettings.setOnClickListener {
            saveSettings()
        }
    }

    private fun saveSettings() {
        workPreference.standardWorkHours = binding.numberPickerHours.value
        workPreference.standardWorkMinutes = binding.numberPickerMinutes.value
        workPreference.isOvertimeNotificationEnabled = binding.switchOvertimeNotification.isChecked

        Toast.makeText(this, "설정이 저장되었습니다.", Toast.LENGTH_SHORT).show()
        finish()
    }
} 