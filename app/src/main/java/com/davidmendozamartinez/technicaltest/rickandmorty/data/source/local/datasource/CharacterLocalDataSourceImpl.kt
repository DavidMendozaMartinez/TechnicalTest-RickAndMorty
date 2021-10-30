package com.davidmendozamartinez.technicaltest.rickandmorty.data.source.local.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.davidmendozamartinez.technicaltest.rickandmorty.data.mapper.CharacterMapper.toDomain
import com.davidmendozamartinez.technicaltest.rickandmorty.data.mapper.CharacterMapper.toLocal
import com.davidmendozamartinez.technicaltest.rickandmorty.data.source.local.RickAndMortyDatabase
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.model.Character
import kotlinx.coroutines.flow.Flow

class CharacterLocalDataSourceImpl(
    private val database: RickAndMortyDatabase
) : CharacterLocalDataSource {

    companion object {
        private const val PAGE_SIZE = 20
        private const val MAX_SIZE = 100
    }

    override fun getFavoriteCharacters(): Flow<PagingData<Character>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = MAX_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                database.characterDao().getFavorites()
                    .map { it.toDomain() }
                    .asPagingSourceFactory().invoke()
            }
        ).flow

    override suspend fun addFavoriteCharacter(character: Character) =
        database.characterDao().addFavorite(character.toLocal())

    override suspend fun removeFavoriteCharacter(character: Character) =
        database.characterDao().deleteFavorite(character.toLocal())

    override suspend fun isFavoriteCharacter(id: Int): Boolean =
        database.characterDao().isFavorite(id)
}
