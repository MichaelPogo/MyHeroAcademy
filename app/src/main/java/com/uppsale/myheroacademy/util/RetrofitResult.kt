package com.uppsale.myheroacademy.util

sealed class RetrofitResult<T>(
    val data: T?=null,
    val message:String?=null
){
    class Success<T>(data: T):RetrofitResult<T>(data)
    class Error<T>(data: T?=null,message: String):RetrofitResult<T>(data,message)
    class Loading<T>:RetrofitResult<T>()
}
