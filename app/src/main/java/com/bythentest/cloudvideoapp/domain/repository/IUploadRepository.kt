package com.bythentest.cloudvideoapp.domain.repository

import com.bythentest.cloudvideoapp.data.remote.network.ApiResponse
import com.bythentest.cloudvideoapp.data.remote.response.UploadVideoResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface IUploadRepository {
    suspend fun uploadVideo(
        file: MultipartBody.Part
    ): Flow<ApiResponse<UploadVideoResponse>>
}