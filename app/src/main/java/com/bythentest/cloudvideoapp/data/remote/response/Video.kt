package com.bythentest.cloudvideoapp.data.remote.response


import com.google.gson.annotations.SerializedName

data class Video(
    @SerializedName("bit_rate")
    val bitRate: String = "",
    @SerializedName("codec")
    val codec: String = "",
    @SerializedName("dar")
    val dar: String = "",
    @SerializedName("level")
    val level: Int = 0,
    @SerializedName("pix_format")
    val pixFormat: String = "",
    @SerializedName("profile")
    val profile: String = "",
    @SerializedName("time_base")
    val timeBase: String = ""
)