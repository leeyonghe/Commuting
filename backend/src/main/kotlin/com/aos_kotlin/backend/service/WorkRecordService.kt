package com.aos_kotlin.backend.service

import com.aos_kotlin.backend.dto.WorkRecordCreateDto
import com.aos_kotlin.backend.dto.WorkRecordDto
import com.aos_kotlin.backend.dto.WorkRecordUpdateDto
import com.aos_kotlin.backend.entity.WorkRecord
import com.aos_kotlin.backend.mapper.WorkRecordMapper
import com.aos_kotlin.backend.repository.WorkRecordRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit

@Service
class WorkRecordService(
    private val workRecordRepository: WorkRecordRepository,
    private val workRecordMapper: WorkRecordMapper
) {
    fun getWorkRecord(id: Long): WorkRecordDto {
        val workRecord = workRecordRepository.findById(id)
            .orElseThrow { NoSuchElementException("Work record not found with id: $id") }
        return workRecordMapper.toDto(workRecord)
    }

    fun getWorkRecordByDate(date: LocalDate): WorkRecordDto? {
        return workRecordRepository.findByDate(date)?.let { workRecordMapper.toDto(it) }
    }

    fun getWorkRecordsBetween(startDate: LocalDate, endDate: LocalDate): List<WorkRecordDto> {
        return workRecordRepository.findByDateBetween(startDate, endDate)
            .map { workRecordMapper.toDto(it) }
    }

    fun getWorkRecordsByYearAndMonth(year: Int, month: Int): List<WorkRecordDto> {
        return workRecordRepository.findByYearAndMonth(year, month)
            .map { workRecordMapper.toDto(it) }
    }

    @Transactional
    fun createWorkRecord(dto: WorkRecordCreateDto): WorkRecordDto {
        val workRecord = workRecordMapper.toEntity(dto)
        val savedWorkRecord = workRecordRepository.save(workRecord)
        return workRecordMapper.toDto(savedWorkRecord)
    }

    @Transactional
    fun updateWorkRecord(id: Long, dto: WorkRecordUpdateDto): WorkRecordDto {
        val workRecord = workRecordRepository.findById(id)
            .orElseThrow { NoSuchElementException("Work record not found with id: $id") }
        
        val updatedWorkRecord = workRecordMapper.updateEntity(workRecord, dto)
        if (dto.endTime != null) {
            updatedWorkRecord.workDuration = calculateWorkDuration(workRecord.startTime, dto.endTime)
        }
        
        val savedWorkRecord = workRecordRepository.save(updatedWorkRecord)
        return workRecordMapper.toDto(savedWorkRecord)
    }

    @Transactional
    fun deleteWorkRecord(id: Long) {
        if (!workRecordRepository.existsById(id)) {
            throw NoSuchElementException("Work record not found with id: $id")
        }
        workRecordRepository.deleteById(id)
    }

    private fun calculateWorkDuration(startTime: LocalTime, endTime: LocalTime): Int {
        return ChronoUnit.MINUTES.between(startTime, endTime).toInt()
    }
} 