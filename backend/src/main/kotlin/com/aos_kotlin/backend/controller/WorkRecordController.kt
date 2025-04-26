package com.aos_kotlin.backend.controller

import com.aos_kotlin.backend.dto.WorkRecordCreateDto
import com.aos_kotlin.backend.dto.WorkRecordDto
import com.aos_kotlin.backend.dto.WorkRecordUpdateDto
import com.aos_kotlin.backend.service.WorkRecordService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/work-records")
class WorkRecordController(private val workRecordService: WorkRecordService) {

    @GetMapping("/{id}")
    fun getWorkRecord(@PathVariable id: Long): ResponseEntity<WorkRecordDto> {
        return ResponseEntity.ok(workRecordService.getWorkRecord(id))
    }

    @GetMapping("/date/{date}")
    fun getWorkRecordByDate(
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate
    ): ResponseEntity<WorkRecordDto?> {
        return ResponseEntity.ok(workRecordService.getWorkRecordByDate(date))
    }

    @GetMapping("/between")
    fun getWorkRecordsBetween(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) startDate: LocalDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) endDate: LocalDate
    ): ResponseEntity<List<WorkRecordDto>> {
        return ResponseEntity.ok(workRecordService.getWorkRecordsBetween(startDate, endDate))
    }

    @GetMapping("/monthly")
    fun getWorkRecordsByYearAndMonth(
        @RequestParam year: Int,
        @RequestParam month: Int
    ): ResponseEntity<List<WorkRecordDto>> {
        return ResponseEntity.ok(workRecordService.getWorkRecordsByYearAndMonth(year, month))
    }

    @PostMapping
    fun createWorkRecord(@RequestBody dto: WorkRecordCreateDto): ResponseEntity<WorkRecordDto> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(workRecordService.createWorkRecord(dto))
    }

    @PutMapping("/{id}")
    fun updateWorkRecord(
        @PathVariable id: Long,
        @RequestBody dto: WorkRecordUpdateDto
    ): ResponseEntity<WorkRecordDto> {
        return ResponseEntity.ok(workRecordService.updateWorkRecord(id, dto))
    }

    @DeleteMapping("/{id}")
    fun deleteWorkRecord(@PathVariable id: Long): ResponseEntity<Void> {
        workRecordService.deleteWorkRecord(id)
        return ResponseEntity.noContent().build()
    }
} 