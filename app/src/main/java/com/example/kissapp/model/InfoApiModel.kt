package com.example.kissapp.model

import com.google.gson.annotations.SerializedName

data class InfoApiModel(

    @SerializedName("info")
    var info: PageModel
)

data class PageModel(
    @SerializedName("pages")
    var pageCount: Int
)