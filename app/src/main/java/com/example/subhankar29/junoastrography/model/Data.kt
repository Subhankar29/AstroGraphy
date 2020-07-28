package com.example.subhankar29.junoastrography.model

import com.google.gson.annotations.SerializedName

data class AdopItems(
    @SerializedName("explanation")
    val explanation: String?,
    @SerializedName("hdurl")
    val hdurl: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("media_type")
    val media_type: String?
)