package com.bythentest.cloudvideoapp.data.remote.response


import com.google.gson.annotations.SerializedName

data class Audio(
    @SerializedName("bit_rate")
    val bitRate: String = "",
    @SerializedName("channel_layout")
    val channelLayout: String = "",
    @SerializedName("channels")
    val channels: Int = 0,
    @SerializedName("codec")
    val codec: String = "",
    @SerializedName("frequency")
    val frequency: Int = 0
)