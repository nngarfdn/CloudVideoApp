package com.bythentest.cloudvideoapp.ui.utils

import android.content.Context
import android.util.Log
import com.arthenica.mobileffmpeg.Config
import com.arthenica.mobileffmpeg.FFmpeg
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import java.io.File

fun compressVideoFile(inputFile: File?, callback: (Boolean, File?) -> Unit) {
    if (inputFile == null || !inputFile.exists()) {
        callback(false, null)
        return
    }
    val parentDir = inputFile.parentFile
    val outputFileName = inputFile.nameWithoutExtension + "_compressed." + inputFile.extension
    val outputFile = File(parentDir, outputFileName)

    val command = arrayOf(
        "-y",                          // Menimpa file output jika sudah ada
        "-i",
        inputFile.absolutePath,  // Input file
        "-vcodec",
        "mpeg4",            // Video codec (gunakan mpeg4 sebagai pengganti libx264 jika tidak tersedia)
        "-qscale:v",
        "2",              // Video quality scale
        "-acodec",
        "aac",              // Audio codec
        "-b:a",
        "128k",                // Audio bitrate
        "-vf",
        "scale=-2:720",         // Video scaling (720p output)
        outputFile.absolutePath        // Output file
    )

    FFmpeg.executeAsync(command) { executionId, returnCode ->
        if (returnCode == Config.RETURN_CODE_SUCCESS) {
            callback(true, outputFile)
        } else {
            Log.e("FFmpeg", "Compression failed with return code $returnCode")
            Log.e("FFmpeg", Config.getLastCommandOutput())
            callback(false, null)
        }
    }
}

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