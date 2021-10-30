package com.davidmendozamartinez.technicaltest.rickandmorty.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorites")
data class CharacterLocal(
    @PrimaryKey @field:SerializedName("id") val id: Int,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("status") val status: String,
    @field:SerializedName("species") val species: String,
    @field:SerializedName("location") val location: String,
    @field:SerializedName("image") val image: String
)
