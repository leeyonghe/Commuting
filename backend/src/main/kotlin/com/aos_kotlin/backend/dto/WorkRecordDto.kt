package com.aos_kotlin.backend.dto

import java.time.LocalDateTime

data class WorkRecordDto(
    val id: Long = 0,
    val date: LocalDateTime,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime? = null,
    val workDuration: Int = 0,
    val notes: String? = null
)

data class WorkRecordCreateDto(
    val date: LocalDateTime,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime? = null,
    val notes: String? = null
)

data class WorkRecordUpdateDto(
    val endTime: LocalDateTime? = null,
    val notes: String? = null
) 