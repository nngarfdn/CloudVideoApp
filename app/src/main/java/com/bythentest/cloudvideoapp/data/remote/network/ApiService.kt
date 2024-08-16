package com.bythentest.cloudvideoapp.data.remote.network

import com.bythentest.cloudvideoapp.data.remote.response.UploadVideoResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Multipart
    @POST("dk3lhojel/video/upload")
    suspend fun uploadVideo(
        @Part file: MultipartBody.Part,
        @Part("upload_preset") uploadPreset: RequestBody,
        @Part("public_id") publicId: RequestBody
    ): UploadVideoResponse
}