package com.bythentest.cloudvideoapp.data.remote

import com.bythentest.cloudvideoapp.data.remote.network.ApiResponse
import com.bythentest.cloudvideoapp.data.remote.network.ApiService
import com.bythentest.cloudvideoapp.data.remote.response.UploadVideoResponse
import com.bythentest.cloudvideoapp.ui.utils.generateRandomString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun uploadVideo(
        file: MultipartBody.Part
    ): Flow<ApiResponse<UploadVideoResponse>> {
        return flow {
            try {
                val response = apiService.uploadVideo(file, ML_DEFAULT
                    .toRequestBody("text/plain".toMediaTypeOrNull()),
                    PUBLIC_ID
                        .toRequestBody("text/plain".toMediaTypeOrNull()))
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e))
            }
        }
    }

    companion object{
        private const val ML_DEFAULT = "ml_default"
        private val PUBLIC_ID = generateRandomString(8)
    }
}
