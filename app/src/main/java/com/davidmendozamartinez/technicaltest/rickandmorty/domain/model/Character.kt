package com.davidmendozamartinez.technicaltest.rickandmorty.domain.model

data class Character(
    val id: Int,
    val name: String,
    val status: Status,
    val species: String,
    val location: Location,
    val image: String
)
