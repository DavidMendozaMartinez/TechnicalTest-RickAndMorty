package com.davidmendozamartinez.technicaltest.rickandmorty.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val name: String
): Parcelable
