package com.bythentest.cloudvideoapp.ui.components.result

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.Player
import androidx.media3.ui.PlayerView
import com.cloudinary.android.cldvideoplayer.CldVideoPlayer

@Composable
fun VideoPlayer(
    videoUrl: String,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context ->
            val pl = CldVideoPlayer(context, videoUrl)
            pl.player.repeatMode = Player.REPEAT_MODE_ALL
            PlayerView(context).apply {
                player = pl.player
                pl.play()
            }
        },
        modifier = modifier
    )
}
