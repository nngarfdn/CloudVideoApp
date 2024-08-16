package com.bythentest.cloudvideoapp.ui.screen.result

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.bythentest.cloudvideoapp.ui.components.result.ResultVideo
import com.bythentest.cloudvideoapp.ui.navigation.Screen


@Composable
fun ResultScreen(
    navController: NavHostController,
    imgUrl: String
) {
    BackHandler { navController.popBackStack() }
    ResultVideo(Screen.Result.getImageUrl(imgUrl))
}
