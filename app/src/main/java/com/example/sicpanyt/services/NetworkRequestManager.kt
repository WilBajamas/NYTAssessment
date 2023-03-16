package com.example.sicpanyt.services

import retrofit2.Response

class NetworkRequestManager {
    suspend fun <T> callApi(apiCall: suspend () -> Response<T>): Result<T> {
        return try {
            val response = apiCall.invoke()

            return if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    Result.success(body)
                } ?: Result.failure(Exception())
            } else {
                Result.failure(Exception())
            }
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}