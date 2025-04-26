package com.aos_kotlin.network

import com.aos_kotlin.model.WorkRecord
import retrofit2.Response
import retrofit2.http.*

interface WorkRecordApiService {
    @GET("api/work-records/{id}")
    suspend fun getWorkRecord(@Path("id") id: Long): Response<WorkRecord>

    @GET("api/work-records/date/{date}")
    suspend fun getWorkRecordByDate(@Path("date") date: String): Response<WorkRecord>

    @GET("api/work-records/between")
    suspend fun getWorkRecordsBetween(
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): Response<List<WorkRecord>>

    @GET("api/work-records/monthly")
    suspend fun getWorkRecordsByYearAndMonth(
        @Query("year") year: Int,
        @Query("month") month: Int
    ): Response<List<WorkRecord>>

    @POST("api/work-records")
    suspend fun createWorkRecord(@Body workRecord: WorkRecord): Response<WorkRecord>

    @PUT("api/work-records/{id}")
    suspend fun updateWorkRecord(
        @Path("id") id: Long,
        @Body workRecord: WorkRecord
    ): Response<WorkRecord>

    @DELETE("api/work-records/{id}")
    suspend fun deleteWorkRecord(@Path("id") id: Long): Response<Unit>
} 