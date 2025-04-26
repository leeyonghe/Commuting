package com.aos_kotlin.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "work_records")
data class WorkRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Date,
    val startTime: Date,
    val endTime: Date? = null,
    val workDuration: Int? = null
) 