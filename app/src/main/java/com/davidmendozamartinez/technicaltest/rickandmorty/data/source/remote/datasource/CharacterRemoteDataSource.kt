package com.davidmendozamartinez.technicaltest.rickandmorty.data.source.remote.datasource

import androidx.paging.PagingData
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRemoteDataSource {
    fun getCharacters(): Flow<PagingData<Character>>
    fun searchCharacters(query: String): Flow<PagingData<Character>>
}
