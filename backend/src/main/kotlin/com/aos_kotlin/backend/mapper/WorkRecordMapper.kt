package com.aos_kotlin.backend.mapper

import com.aos_kotlin.backend.dto.WorkRecordCreateDto
import com.aos_kotlin.backend.dto.WorkRecordDto
import com.aos_kotlin.backend.dto.WorkRecordUpdateDto
import com.aos_kotlin.backend.entity.WorkRecord
import org.springframework.stereotype.Component

@Component
class WorkRecordMapper {
    fun toDto(entity: WorkRecord): WorkRecordDto {
        return WorkRecordDto(
            id = entity.id,
            date = entity.date,
            startTime = entity.startTime,
            endTime = entity.endTime,
            workDuration = entity.workDuration,
            notes = entity.notes
        )
    }

    fun toEntity(dto: WorkRecordCreateDto): WorkRecord {
        return WorkRecord(
            date = dto.date,
            startTime = dto.startTime,
            endTime = dto.endTime,
            notes = dto.notes
        )
    }

    fun updateEntity(entity: WorkRecord, dto: WorkRecordUpdateDto): WorkRecord {
        return entity.copy(
            endTime = dto.endTime ?: entity.endTime,
            notes = dto.notes ?: entity.notes
        )
    }
} 