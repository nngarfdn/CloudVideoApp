package com.bythentest.cloudvideoapp.data.remote.response


import com.google.gson.annotations.SerializedName

data class UploadVideoResponse(
    @SerializedName("asset_folder")
    val assetFolder: String = "",
    @SerializedName("asset_id")
    val assetId: String = "",
    @SerializedName("audio")
    val audio: Audio = Audio(),
    @SerializedName("bit_rate")
    val bitRate: Int = 0,
    @SerializedName("bytes")
    val bytes: Int = 0,
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("display_name")
    val displayName: String = "",
    @SerializedName("duration")
    val duration: Double = 0.0,
    @SerializedName("etag")
    val etag: String = "",
    @SerializedName("existing")
    val existing: Boolean = false,
    @SerializedName("format")
    val format: String = "",
    @SerializedName("frame_rate")
    val frameRate: Double = 0.0,
    @SerializedName("height")
    val height: Int = 0,
    @SerializedName("is_audio")
    val isAudio: Boolean = false,
    @SerializedName("nb_frames")
    val nbFrames: Int = 0,
    @SerializedName("original_filename")
    val originalFilename: String = "",
    @SerializedName("pages")
    val pages: Int = 0,
    @SerializedName("placeholder")
    val placeholder: Boolean = false,
    @SerializedName("playback_url")
    val playbackUrl: String = "",
    @SerializedName("public_id")
    val publicId: String = "",
    @SerializedName("resource_type")
    val resourceType: String = "",
    @SerializedName("rotation")
    val rotation: Int = 0,
    @SerializedName("secure_url")
    val secureUrl: String = "",
    @SerializedName("signature")
    val signature: String = "",
    @SerializedName("tags")
    val tags: List<Any> = listOf(),
    @SerializedName("type")
    val type: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("version")
    val version: Int = 0,
    @SerializedName("version_id")
    val versionId: String = "",
    @SerializedName("video")
    val video: Video = Video(),
    @SerializedName("width")
    val width: Int = 0
)