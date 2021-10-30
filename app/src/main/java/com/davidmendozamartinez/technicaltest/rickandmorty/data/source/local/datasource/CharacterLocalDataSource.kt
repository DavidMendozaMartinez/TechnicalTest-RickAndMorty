package com.davidmendozamartinez.technicaltest.rickandmorty.data.source.local.datasource

import androidx.paging.PagingData
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterLocalDataSource {
    fun getFavoriteCharacters(): Flow<PagingData<Character>>
    suspend fun addFavoriteCharacter(character: Character)
    suspend fun removeFavoriteCharacter(character: Character)
    suspend fun isFavoriteCharacter(id: Int): Boolean
}
