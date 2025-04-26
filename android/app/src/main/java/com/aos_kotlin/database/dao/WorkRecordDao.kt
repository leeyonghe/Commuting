package com.aos_kotlin.database.dao

import androidx.room.*
import com.aos_kotlin.model.WorkRecord
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface WorkRecordDao {
    @Query("SELECT * FROM work_records WHERE date = :date")
    fun getWorkRecordByDate(date: Date): WorkRecord?

    @Query("SELECT * FROM work_records WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getWorkRecordsByDateRange(startDate: Date, endDate: Date): Flow<List<WorkRecord>>

    @Query("SELECT * FROM work_records WHERE strftime('%Y-%m', date/1000, 'unixepoch') = :yearMonth ORDER BY date DESC")
    fun getWorkRecordsByYearMonth(yearMonth: String): Flow<List<WorkRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWorkRecord(workRecord: WorkRecord): Long

    @Update
    fun updateWorkRecord(workRecord: WorkRecord): Int

    @Delete
    fun deleteWorkRecord(workRecord: WorkRecord): Int
} 