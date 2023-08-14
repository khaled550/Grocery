package com.khaled.grocery.model

import retrofit2.Response

sealed class State<out T>{
    data class Success<T>(val data: T) : State<T>(){}
    data class Fail(val msg: String) : State<Nothing>(){}
    object Loading : State<Nothing>(){}
}
