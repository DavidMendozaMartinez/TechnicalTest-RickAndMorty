package com.davidmendozamartinez.technicaltest.rickandmorty.data.source.remote.model.response

import com.google.gson.annotations.SerializedName

class ListResponse<T>(
    @SerializedName("info") val info: PaginationInfoResponse,
    @SerializedName("results") val results: List<T>,
)
