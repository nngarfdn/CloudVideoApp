package com.bythentest.cloudvideoapp.data.remote

import com.bythentest.cloudvideoapp.data.remote.network.ApiResponse
import com.bythentest.cloudvideoapp.data.remote.response.UploadVideoResponse
import com.bythentest.cloudvideoapp.domain.repository.IUploadRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadRepository(private val remoteDataSource: RemoteDataSource): IUploadRepository {
    override suspend fun uploadVideo(
        file: MultipartBody.Part
    ): Flow<ApiResponse<UploadVideoResponse>> {
        return remoteDataSource.uploadVideo(file)
    }
}