package com.atitus.possoajudar.repositories

import com.atitus.possoajudar.model.History
import com.atitus.possoajudar.services.HistoriesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HistoriesRepository @Inject constructor() {

    @Inject lateinit var historiesService: HistoriesService

    fun getHistories(): Flow<List<History>> = flow {
        emit(historiesService.getHistories())
    }.flowOn(Dispatchers.IO)

}