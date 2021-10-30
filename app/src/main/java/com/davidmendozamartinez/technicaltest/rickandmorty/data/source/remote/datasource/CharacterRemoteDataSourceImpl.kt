package com.davidmendozamartinez.technicaltest.rickandmorty.data.source.remote.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.davidmendozamartinez.technicaltest.rickandmorty.data.source.remote.RickAndMortyApiService
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.model.Character
import kotlinx.coroutines.flow.Flow

class CharacterRemoteDataSourceImpl(
    private val service: RickAndMortyApiService
) : CharacterRemoteDataSource {

    companion object {
        private const val PAGE_SIZE = 20
        private const val MAX_SIZE = 100
    }

    override fun getCharacters(): Flow<PagingData<Character>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = MAX_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CharacterPagingSource(service) }
        ).flow

    override fun searchCharacters(query: String): Flow<PagingData<Character>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = MAX_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CharacterPagingSource(service, query) }
        ).flow
}
