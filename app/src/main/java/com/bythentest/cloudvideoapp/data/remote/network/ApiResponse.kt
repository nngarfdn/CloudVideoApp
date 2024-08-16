package com.bythentest.cloudvideoapp.data.remote.network

sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val errorMessage: Exception) : ApiResponse<Nothing>()
    data object Empty : ApiResponse<Nothing>()
}