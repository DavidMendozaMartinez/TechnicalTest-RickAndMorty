package com.davidmendozamartinez.technicaltest.rickandmorty.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class CharacterRemote(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("species") val species: String,
    @SerializedName("location") val location: LocationRemote,
    @SerializedName("image") val image: String
)
