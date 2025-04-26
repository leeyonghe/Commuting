package com.aos_kotlin.backend.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "work_records")
data class WorkRecord(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val date: LocalDateTime,

    @Column(nullable = false)
    val startTime: LocalDateTime,

    @Column
    val endTime: LocalDateTime? = null,

    @Column
    val workDuration: Int = 0,

    @Column
    val notes: String? = null
) 