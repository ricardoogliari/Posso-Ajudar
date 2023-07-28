package com.atitus.possoajudar.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atitus.possoajudar.model.History
import com.atitus.possoajudar.repositories.HistoriesRepository
import com.atitus.possoajudar.services.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoriesViewModel @Inject constructor() : ViewModel() {

    @Inject lateinit var repository: HistoriesRepository

    var histories: MutableList<History> = mutableListOf()
    val response: MutableLiveData<ApiState> = MutableLiveData(ApiState.Empty)

    fun fetchHistories() =
        viewModelScope.launch {
            repository.getHistories().onStart {
                response.value = ApiState.Loading
            }.catch {
                response.value = ApiState.Failure(it)
            }.collect {
                histories = it.toMutableList()
                response.value = ApiState.Success(histories)
            }
        }

    fun addHistory(history: History) {
        histories.add(history)
        response.value = ApiState.Success(histories)
    }

}