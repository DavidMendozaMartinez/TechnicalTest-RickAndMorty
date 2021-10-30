package com.davidmendozamartinez.technicaltest.rickandmorty.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class LocationRemote(
    @SerializedName("name") val name: String
)
