package com.bythentest.cloudvideoapp.ui.components.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingIndicatorWithPercent(progress: Int) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = {
                        progress / 100f
                    },
                    modifier = Modifier.size(64.dp),
                    color = Color.White,
                    strokeWidth = 4.dp,
                )
                Text(
                    text = "$progress%",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = "Uploading video...",
                color = Color.White,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
