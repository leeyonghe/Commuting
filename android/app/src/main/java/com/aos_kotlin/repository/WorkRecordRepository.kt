package com.aos_kotlin.repository

import com.aos_kotlin.database.WorkRecordDao
import com.aos_kotlin.model.WorkRecord
import com.aos_kotlin.network.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WorkRecordRepository(private val workRecordDao: WorkRecordDao) {
    private val apiService = NetworkModule.workRecordApiService
    private val dateFormatter = DateTimeFormatter.ISO_DATE_TIME

    suspend fun getWorkRecord(id: Long): WorkRecord? = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getWorkRecord(id)
            if (response.isSuccessful) {
                response.body()?.let { workRecord ->
                    workRecordDao.insert(workRecord)
                    workRecord
                }
            } else {
                workRecordDao.getWorkRecordById(id)
            }
        } catch (e: Exception) {
            workRecordDao.getWorkRecordById(id)
        }
    }

    suspend fun getWorkRecordByDate(date: LocalDateTime): WorkRecord? = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getWorkRecordByDate(date.format(dateFormatter))
            if (response.isSuccessful) {
                response.body()?.let { workRecord ->
                    workRecordDao.insert(workRecord)
                    workRecord
                }
            } else {
                workRecordDao.getWorkRecordByDate(date)
            }
        } catch (e: Exception) {
            workRecordDao.getWorkRecordByDate(date)
        }
    }

    suspend fun getWorkRecordsBetween(startDate: LocalDateTime, endDate: LocalDateTime): List<WorkRecord> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.getWorkRecordsBetween(
                    startDate.format(dateFormatter),
                    endDate.format(dateFormatter)
                )
                if (response.isSuccessful) {
                    response.body()?.let { workRecords ->
                        workRecords.forEach { workRecordDao.insert(it) }
                        workRecords
                    } ?: emptyList()
                } else {
                    workRecordDao.getWorkRecordsBetween(startDate, endDate)
                }
            } catch (e: Exception) {
                workRecordDao.getWorkRecordsBetween(startDate, endDate)
            }
        }

    suspend fun getWorkRecordsByYearAndMonth(year: Int, month: Int): List<WorkRecord> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.getWorkRecordsByYearAndMonth(year, month)
                if (response.isSuccessful) {
                    response.body()?.let { workRecords ->
                        workRecords.forEach { workRecordDao.insert(it) }
                        workRecords
                    } ?: emptyList()
                } else {
                    workRecordDao.getWorkRecordsByYearAndMonth(year, month)
                }
            } catch (e: Exception) {
                workRecordDao.getWorkRecordsByYearAndMonth(year, month)
            }
        }

    suspend fun createWorkRecord(workRecord: WorkRecord): WorkRecord = withContext(Dispatchers.IO) {
        try {
            val response = apiService.createWorkRecord(workRecord)
            if (response.isSuccessful) {
                response.body()?.let { savedWorkRecord ->
                    workRecordDao.insert(savedWorkRecord)
                    savedWorkRecord
                } ?: workRecord
            } else {
                workRecordDao.insert(workRecord)
                workRecord
            }
        } catch (e: Exception) {
            workRecordDao.insert(workRecord)
            workRecord
        }
    }

    suspend fun updateWorkRecord(workRecord: WorkRecord): WorkRecord = withContext(Dispatchers.IO) {
        try {
            val response = apiService.updateWorkRecord(workRecord.id, workRecord)
            if (response.isSuccessful) {
                response.body()?.let { updatedWorkRecord ->
                    workRecordDao.update(updatedWorkRecord)
                    updatedWorkRecord
                } ?: workRecord
            } else {
                workRecordDao.update(workRecord)
                workRecord
            }
        } catch (e: Exception) {
            workRecordDao.update(workRecord)
            workRecord
        }
    }

    suspend fun deleteWorkRecord(id: Long) = withContext(Dispatchers.IO) {
        try {
            val response = apiService.deleteWorkRecord(id)
            if (response.isSuccessful) {
                workRecordDao.deleteById(id)
            }
        } catch (e: Exception) {
            workRecordDao.deleteById(id)
        }
    }
} 