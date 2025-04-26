package com.aos_kotlin.backend.repository

import com.aos_kotlin.backend.entity.WorkRecord
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface WorkRecordRepository : JpaRepository<WorkRecord, Long> {
    fun findByDate(date: LocalDateTime): WorkRecord?

    fun findByDateBetween(startDate: LocalDateTime, endDate: LocalDateTime): List<WorkRecord>

    @Query("SELECT w FROM WorkRecord w WHERE YEAR(w.date) = :year AND MONTH(w.date) = :month")
    fun findByYearAndMonth(@Param("year") year: Int, @Param("month") month: Int): List<WorkRecord>
} 