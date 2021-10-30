package com.davidmendozamartinez.technicaltest.rickandmorty.data.repository

import androidx.paging.PagingData
import com.davidmendozamartinez.technicaltest.rickandmorty.data.source.local.datasource.CharacterLocalDataSource
import com.davidmendozamartinez.technicaltest.rickandmorty.data.source.remote.datasource.CharacterRemoteDataSource
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.model.Character
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

class CharacterRepositoryImpl(
    private val remoteDataSource: CharacterRemoteDataSource,
    private val localDataSource: CharacterLocalDataSource
) : CharacterRepository {
    override fun getCharacters(): Flow<PagingData<Character>> =
        remoteDataSource.getCharacters()

    override fun getFavoriteCharacters(): Flow<PagingData<Character>> =
        localDataSource.getFavoriteCharacters()

    override fun searchCharacters(query: String): Flow<PagingData<Character>> =
        remoteDataSource.searchCharacters(query)

    override suspend fun addFavoriteCharacter(character: Character) =
        localDataSource.addFavoriteCharacter(character)

    override suspend fun removeFavoriteCharacter(character: Character) =
        localDataSource.removeFavoriteCharacter(character)

    override suspend fun isFavoriteCharacter(id: Int): Boolean =
        localDataSource.isFavoriteCharacter(id)
}
