package com.davidmendozamartinez.technicaltest.rickandmorty.data.source.remote.model.response

import com.google.gson.annotations.SerializedName

class PaginationInfoResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("next") val next: String,
    @SerializedName("prev") val prev: String,
)
