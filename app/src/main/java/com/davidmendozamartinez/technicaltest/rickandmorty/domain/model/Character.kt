package com.davidmendozamartinez.technicaltest.rickandmorty.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val location: Location,
    val image: String
): Parcelable {
    val data get() = "$species · $status"
}
