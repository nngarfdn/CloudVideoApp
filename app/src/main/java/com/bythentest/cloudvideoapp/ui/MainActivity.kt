package com.bythentest.cloudvideoapp.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.Recording
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.video.AudioConfig
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.bythentest.cloudvideoapp.ui.screen.MainScreen
import com.bythentest.cloudvideoapp.ui.theme.CloudVideoAppTheme
import java.io.File


class MainActivity : ComponentActivity() {

    private var recording: Recording? = null
    private var isVideoSavedSuccessfully = mutableStateOf(false)
    private val mutableFileFile = mutableStateOf<File?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!hasRequiredPermissions()) {
            ActivityCompat.requestPermissions(
                this, CAMERAX_PERMISSIONS, 0
            )
        }

        setContent {
            CloudVideoAppTheme {
                val isRecording = remember { mutableStateOf(false) }
                val isUploading = remember { mutableStateOf(false) }
                val uploadProgress = remember { mutableIntStateOf(0) }
                val fileVideo = remember { mutableFileFile }
                val navController = rememberNavController()
                val controller = remember {
                    LifecycleCameraController(applicationContext).apply {
                        setEnabledUseCases(CameraController.IMAGE_CAPTURE or
                                CameraController.VIDEO_CAPTURE
                        )
                    }
                }
                MainScreen(
                    navController = navController,
                    fileVideo = fileVideo,
                    controller = controller,
                    isRecording = isRecording,
                    isVideoSavedSuccessfully = isVideoSavedSuccessfully,
                    uploadProgress = uploadProgress,
                    isUploading = isUploading,
                    startRecording = this::startRecording,
                    stopRecording = this::stopRecording,
                    hasRequiredPermissions = this::hasRequiredPermissions
                )
            }
        }

    }

    private fun startRecording(
        controller: LifecycleCameraController,
        onResult: (VideoRecordEvent) -> Unit,
        hasRequiredPermissions: Boolean
    ) {
        if (!hasRequiredPermissions) {
            Toast.makeText(
                applicationContext,
                "Required permissions are missing.",
                Toast.LENGTH_LONG
            ).show()
            return
        }
        stopRecording()
        val outputFile = File(filesDir, VIDEO_NAME)
        this.mutableFileFile.value = outputFile
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(
                applicationContext,
                "RECORD_AUDIO permission not granted",
                Toast.LENGTH_LONG
            ).show()
            return
        }
        recording = controller.startRecording(
            FileOutputOptions.Builder(outputFile).build(),
            AudioConfig.create(true),
            ContextCompat.getMainExecutor(applicationContext),
        ) { event ->
            onResult(event)
            if (event is VideoRecordEvent.Finalize) {
                if (event.hasError()) {
                    isVideoSavedSuccessfully.value = false
                    Toast.makeText(
                        applicationContext,
                        "Video recording failed with error: ${event.cause?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    isVideoSavedSuccessfully.value = true
                }
                recording?.close()
                recording = null
            }
        }
        return
    }


    private fun stopRecording() {
        recording?.apply {
            stop()
            recording = null
        }
    }

    private fun hasRequiredPermissions(): Boolean {
        return CAMERAX_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }


    companion object {
        private val CAMERAX_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
        )
        private const val VIDEO_NAME = "my-recording.mp4"
    }
}

