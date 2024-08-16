package com.bythentest.cloudvideoapp.ui.navigation

import com.bythentest.cloudvideoapp.ui.utils.encodeUrl
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

sealed class Screen (val route: String){
    data object Camera: Screen("camera")
    data object Result : Screen("detail/{imgUrl}") {
        fun createRoute(imgUrl: String): String = "detail/${encodeUrl(imgUrl)}"
        fun getImageUrl(route: String): String {
            return URLDecoder.decode(route.substringAfter("detail/"), StandardCharsets.UTF_8.toString())
        }
    }
}