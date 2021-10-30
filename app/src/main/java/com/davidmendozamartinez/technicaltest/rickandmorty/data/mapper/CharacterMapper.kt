package com.davidmendozamartinez.technicaltest.rickandmorty.data.mapper

import com.davidmendozamartinez.technicaltest.rickandmorty.data.mapper.LocationMapper.toDomain
import com.davidmendozamartinez.technicaltest.rickandmorty.data.source.remote.model.CharacterRemote
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.model.Character
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
}
