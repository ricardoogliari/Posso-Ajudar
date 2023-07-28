package com.atitus.possoajudar.services

import com.atitus.possoajudar.model.History
import retrofit2.http.GET

interface HistoriesService {
    @GET("post")
    suspend fun getHistories(): List<History>
}