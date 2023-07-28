package com.atitus.possoajudar.services

sealed class ApiState {
    class Success<T>(val data: T) : ApiState()
    class Failure(val msg: Throwable) : ApiState()
    object Loading:ApiState()
    object Empty: ApiState()
}