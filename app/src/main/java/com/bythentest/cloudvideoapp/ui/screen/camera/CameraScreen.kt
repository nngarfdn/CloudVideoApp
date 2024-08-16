package com.bythentest.cloudvideoapp.ui.screen.camera

import android.annotation.SuppressLint
import androidx.camera.core.CameraSelector
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.bythentest.cloudvideoapp.ui.components.loading.LoadingIndicatorWithPercent
import com.bythentest.cloudvideoapp.ui.components.camera.CameraPreview
import com.bythentest.cloudvideoapp.ui.utils.isInternetAvailable
import com.bythentest.cloudvideoapp.ui.utils.showToastLong
import kotlinx.coroutines.delay


@SuppressLint("DefaultLocale")
@Composable
fun CameraScreen(
    controller: LifecycleCameraController,
    isRecording: MutableState<Boolean>,
    isVideoSavedSuccessfully: MutableState<Boolean>,
    uploadProgress: MutableState<Int>,
    isUploading: MutableState<Boolean>,
    onRecordVideo: (start: Boolean, onResult: (VideoRecordEvent) -> Unit) -> Unit,
    onSuccessRecordVideo: () -> Unit,
    onFailRecordVideo: () -> Unit,
    onUploadClicked: () -> Unit,
) {
    val recordingTime = remember { mutableStateOf("00:00") }
    LaunchedEffect(isRecording.value) {
        if (isRecording.value) {
            var seconds = 0
            while (isRecording.value) {
                String.format("%02d:%02d", seconds / 60, seconds % 60)
                    .also { recordingTime.value = it }
                delay(1000L) // Delay for 1 second
                seconds++
            }
        } else {
            recordingTime.value = "00:00" // Reset the timer when recording stops
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CameraPreview(
            controller = controller,
            modifier = Modifier.fillMaxSize()
        )

        IconButton(
            onClick = {
                controller.cameraSelector = if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                    CameraSelector.DEFAULT_FRONT_CAMERA
                } else {
                    CameraSelector.DEFAULT_BACK_CAMERA
                }
            },
            modifier = Modifier
                .offset(16.dp, 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Cameraswitch,
                contentDescription = "Switch camera",
                tint = Color.White
            )
        }
        if (isRecording.value) {
            Text(
                text = recordingTime.value,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 80.dp) // Adjust the padding as needed
                    .background(Color.Red, shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(Color.Black.copy(alpha = 0.2f))
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            IconButton(
                onClick = {
                    val newRecordingState = !isRecording.value
                    onRecordVideo(newRecordingState) { event ->
                        if (event is VideoRecordEvent.Finalize) {
                            if (event.hasError()) {
                                isVideoSavedSuccessfully.value = false
                                onFailRecordVideo()
                            } else {
                                isVideoSavedSuccessfully.value = true
                                onSuccessRecordVideo()
                            }
                        }
                    }
                    isRecording.value = newRecordingState
                }
            ) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    imageVector = if (isRecording.value) Icons.Default.Stop else Icons.Default.Videocam,
                    contentDescription = if (isRecording.value) "Stop recording" else "Record video",
                    tint = Color.White
                )
            }

            if (isVideoSavedSuccessfully.value) {
                IconButton(
                    onClick = {
                        isUploading.value = true
                        onUploadClicked()
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(48.dp),
                        imageVector = Icons.Default.ArrowCircleUp,
                        contentDescription = "Upload video",
                        tint = Color.White
                    )
                }
            }
        }
        if (isUploading.value) {
            val context = LocalContext.current
            if (!isInternetAvailable(context)) {
                isUploading.value = false
                context.showToastLong("No internet connection")
            }else{
                LoadingIndicatorWithPercent(progress = uploadProgress.value)
            }
        }
    }
}



