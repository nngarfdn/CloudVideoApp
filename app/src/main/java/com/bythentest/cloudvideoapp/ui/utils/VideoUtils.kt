package com.bythentest.cloudvideoapp.ui.utils

import android.content.Context
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import java.io.File


fun uploadToCloudinary(
    context: Context,
    fileVideo: File?,
    onProgress: (
        percent: Int,
    ) -> Unit,
    onSuccess: (resultData: Map<*, *>?) -> Unit,
    onError: (error: ErrorInfo) -> Unit
) {
    MediaManager.get()
        .upload(fileVideo?.absolutePath)
        .option("resource_type", "video")
        .option("upload_preset", "ml_default")
        .option("public_id", generateRandomString(10))
        .callback(
            object : UploadCallback {
                override fun onStart(requestId: String) {
                }

                override fun onProgress(
                    requestId: String,
                    bytes: Long,
                    totalBytes: Long
                ) {
                    val percentage = (bytes * 100 / totalBytes).toInt()
                    onProgress(percentage)
                }

                override fun onSuccess(
                    requestId: String,
                    resultData: Map<*, *>?
                ) {
                    onSuccess(resultData)
                }

                override fun onError(requestId: String, error: ErrorInfo) {
                    onError(error)
                }

                override fun onReschedule(requestId: String, error: ErrorInfo) {
                }

            }
        )
        .dispatch(context)
}