package com.bythentest.cloudvideoapp.ui.screen

import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bythentest.cloudvideoapp.ui.navigation.Screen
import com.bythentest.cloudvideoapp.ui.screen.camera.CameraScreen
import com.bythentest.cloudvideoapp.ui.screen.result.ResultScreen
import com.bythentest.cloudvideoapp.ui.utils.encodeUrl
import com.bythentest.cloudvideoapp.ui.utils.showToastLong
import com.bythentest.cloudvideoapp.ui.utils.uploadToCloudinary
import java.io.File

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    fileVideo: MutableState<File?>,
    controller: LifecycleCameraController,
    isRecording: MutableState<Boolean>,
    isVideoSavedSuccessfully: MutableState<Boolean>,
    uploadProgress: MutableState<Int>,
    isUploading: MutableState<Boolean>,
    startRecording: (controller: LifecycleCameraController, onResult: (VideoRecordEvent) -> Unit, hasPermissions: Boolean) -> Unit,
    stopRecording: () -> Unit,
    hasRequiredPermissions: () -> Boolean
) {
    NavHost(navController, startDestination = Screen.Camera.route) {
        composable(Screen.Camera.route) {
            CameraScreen(
                controller = controller,
                isRecording = isRecording,
                isVideoSavedSuccessfully = isVideoSavedSuccessfully,
                uploadProgress = uploadProgress,
                isUploading = isUploading,
                onRecordVideo = { start, onResult ->
                    if (start) {
                        startRecording(controller, onResult, hasRequiredPermissions())
                    } else {
                        stopRecording()
                    }
                },
                onSuccessRecordVideo = {},
                onFailRecordVideo = {
                    navController.context.showToastLong("Video capture failed")
                },
                onUploadClicked = {
                    uploadToCloudinary(
                        context = navController.context,
                        fileVideo = fileVideo.value,
                        onProgress = { percent ->
                            uploadProgress.value = percent
                        },
                        onSuccess = { resultData ->
                            isUploading.value = false
                            navController.context.showToastLong("Video uploaded successfully")
                            val encodedUrl =
                                encodeUrl(resultData?.get("secure_url") as? String ?: "")
                            navController.navigate(Screen.Result.createRoute(encodedUrl))
                        },
                        onError = { error ->
                            isUploading.value = false
                            navController.context.showToastLong("Video upload failed: ${error.description}")
                        }
                    )
                },
            )
        }
        composable(Screen.Result.route) { backStackEntry ->
            val imgUrl = backStackEntry.arguments?.getString("imgUrl") ?: ""
            ResultScreen(navController= navController, imgUrl = imgUrl)
        }
    }
}