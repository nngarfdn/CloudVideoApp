package com.bythentest.cloudvideoapp.domain.usecase

import com.bythentest.cloudvideoapp.data.remote.network.ApiResponse
import com.bythentest.cloudvideoapp.data.remote.response.UploadVideoResponse
import com.bythentest.cloudvideoapp.domain.repository.IUploadRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadUseCase(private val uploadRepository: IUploadRepository) {
    suspend fun uploadVideo(
        file: MultipartBody.Part
    ): Flow<ApiResponse<UploadVideoResponse>>{
        return uploadRepository.uploadVideo(file)
    }
}