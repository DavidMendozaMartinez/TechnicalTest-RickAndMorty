package com.davidmendozamartinez.technicaltest.rickandmorty.domain.usecase

import androidx.paging.PagingData
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.model.Character
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

class SearchCharactersUseCase(private val repository: CharacterRepository) {
    fun invoke(query: String): Flow<PagingData<Character>> = repository.searchCharacters(query)
}
