package com.davidmendozamartinez.technicaltest.rickandmorty.data.repository

import androidx.paging.PagingData
import com.davidmendozamartinez.technicaltest.rickandmorty.data.mapper.CharacterMapper.toDomain
import com.davidmendozamartinez.technicaltest.rickandmorty.data.source.remote.sampleCharacters
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.model.Character
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeCharacterRepositoryImpl: CharacterRepository {
    var shouldReturnError: Boolean = false

    override fun getCharacters(): Flow<PagingData<Character>> =
    if (!shouldReturnError) flowOf(PagingData.from(sampleCharacters.map { it.toDomain() }))
    else throw AssertionError()

    override fun getFavoriteCharacters(): Flow<PagingData<Character>> =
        if (!shouldReturnError) flowOf(PagingData.from(sampleCharacters.map { it.toDomain() }))
        else throw AssertionError()

    override fun searchCharacters(query: String): Flow<PagingData<Character>> =
        if (!shouldReturnError) flowOf(PagingData.from(sampleCharacters.map { it.toDomain() }))
        else throw AssertionError()

    override suspend fun addFavoriteCharacter(character: Character) {}

    override suspend fun removeFavoriteCharacter(character: Character) {}

    override suspend fun isFavoriteCharacter(id: Int): Boolean = true
}