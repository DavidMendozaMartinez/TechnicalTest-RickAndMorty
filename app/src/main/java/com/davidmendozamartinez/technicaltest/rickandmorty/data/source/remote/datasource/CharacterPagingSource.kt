package com.davidmendozamartinez.technicaltest.rickandmorty.data.source.remote.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.davidmendozamartinez.technicaltest.rickandmorty.data.mapper.CharacterMapper.toDomain
import com.davidmendozamartinez.technicaltest.rickandmorty.data.source.remote.RickAndMortyApiService
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.model.Character
import retrofit2.HttpException
import java.io.IOException

private const val RICK_AND_MORTY_API_STARTING_PAGE_INDEX = 1

class CharacterPagingSource(
    private val service: RickAndMortyApiService,
    private val query: String = ""
) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val page = params.key ?: RICK_AND_MORTY_API_STARTING_PAGE_INDEX

        return try {
            val response = if (query.isBlank()) service.getCharacters(page)
            else service.searchCharacters(query, page)

            val data = response.results.map { it.toDomain() }
            val prevKey = if (page == RICK_AND_MORTY_API_STARTING_PAGE_INDEX) null else page - 1
            val nextKey = if (page >= response.info.pages) null else page + 1

            LoadResult.Page(data, prevKey, nextKey)
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
}
