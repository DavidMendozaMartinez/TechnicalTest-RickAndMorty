package com.davidmendozamartinez.technicaltest.rickandmorty.domain.usecase

import androidx.paging.PagingData
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.model.Character
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteCharactersUseCase(private val repository: CharacterRepository) {
    fun invoke(): Flow<PagingData<Character>> = repository.getFavoriteCharacters()
}
