package com.davidmendozamartinez.technicaltest.rickandmorty.data.mapper

import com.davidmendozamartinez.technicaltest.rickandmorty.data.mapper.LocationMapper.toDomain
import com.davidmendozamartinez.technicaltest.rickandmorty.data.source.local.model.CharacterLocal
import com.davidmendozamartinez.technicaltest.rickandmorty.data.source.remote.model.CharacterRemote
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.model.Character
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.model.Location
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.model.Status

object CharacterMapper {
    fun CharacterRemote.toDomain(): Character = Character(
        id,
        name,
        Status.valueOf(status),
        species,
        location.toDomain(),
        image
    )

    fun CharacterLocal.toDomain(): Character = Character(
        id,
        name,
        Status.valueOf(status),
        species,
        Location(location),
        image
    )

    fun Character.toLocal(): CharacterLocal = CharacterLocal(
        id,
        name,
        status.name,
        species,
        location.name,
        image
    )
}
