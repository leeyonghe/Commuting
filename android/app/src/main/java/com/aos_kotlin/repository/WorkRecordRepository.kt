package com.aos_kotlin.repository

import com.aos_kotlin.database.dao.WorkRecordDao
import com.aos_kotlin.model.WorkRecord
import kotlinx.coroutines.flow.Flow
import java.util.Date

class WorkRecordRepository(private val workRecordDao: WorkRecordDao) {
    fun getWorkRecordByDate(date: Date): WorkRecord? {
        return workRecordDao.getWorkRecordByDate(date)
    }

    fun getWorkRecordsByDateRange(startDate: Date, endDate: Date): Flow<List<WorkRecord>> {
        return workRecordDao.getWorkRecordsByDateRange(startDate, endDate)
    }

    fun getWorkRecordsByYearMonth(yearMonth: String): Flow<List<WorkRecord>> {
        return workRecordDao.getWorkRecordsByYearMonth(yearMonth)
    }

    suspend fun insertWorkRecord(workRecord: WorkRecord) {
        workRecordDao.insertWorkRecord(workRecord)
    }

    suspend fun updateWorkRecord(workRecord: WorkRecord) {
        workRecordDao.updateWorkRecord(workRecord)
    }

    suspend fun deleteWorkRecord(workRecord: WorkRecord) {
        workRecordDao.deleteWorkRecord(workRecord)
    }
} 