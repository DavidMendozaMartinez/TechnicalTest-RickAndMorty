package com.davidmendozamartinez.technicaltest.rickandmorty.data.mapper

import com.davidmendozamartinez.technicaltest.rickandmorty.data.source.remote.model.LocationRemote
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.model.Location

object LocationMapper {
    fun LocationRemote.toDomain(): Location = Location(name)
}
