package com.atitus.possoajudar.model

import com.google.gson.annotations.SerializedName

data class History(
    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double,

    @SerializedName("like")
    var like: Int = 0,

    @SerializedName("dislike")
    var dislike: Int = 0,
)