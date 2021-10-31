package com.davidmendozamartinez.technicaltest.rickandmorty.data.source.remote

import com.davidmendozamartinez.technicaltest.rickandmorty.data.source.remote.model.CharacterRemote
import com.davidmendozamartinez.technicaltest.rickandmorty.data.source.remote.model.LocationRemote

val sampleCharacters: List<CharacterRemote> = listOf(
    CharacterRemote(
        1,
        "Name1",
        "Status1",
        "Species1",
        LocationRemote("Location1"),
        "Image1"
    ),
    CharacterRemote(
        2,
        "Name2",
        "Status2",
        "Species2",
        LocationRemote("Location2"),
        "Image2"
    ),
    CharacterRemote(
        3,
        "Name3",
        "Status3",
        "Species3",
        LocationRemote("Location3"),
        "Image3"
    )
)
