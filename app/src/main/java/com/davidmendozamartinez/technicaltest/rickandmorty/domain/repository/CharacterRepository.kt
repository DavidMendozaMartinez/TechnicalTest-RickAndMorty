package com.davidmendozamartinez.technicaltest.rickandmorty.domain.repository

import androidx.paging.PagingData
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharacters(): Flow<PagingData<Character>>
    fun getFavoriteCharacters(): Flow<PagingData<Character>>
    fun searchCharacters(query: String): Flow<PagingData<Character>>
    suspend fun addFavoriteCharacter(character: Character)
    suspend fun removeFavoriteCharacter(character: Character)
    suspend fun isFavoriteCharacter(id: Int): Boolean
}
