package com.davidmendozamartinez.technicaltest.rickandmorty.domain.usecase

import com.davidmendozamartinez.technicaltest.rickandmorty.domain.repository.CharacterRepository

class IsFavoriteCharacterUseCase(private val repository: CharacterRepository) {
    suspend fun invoke(id: Int): Boolean = repository.isFavoriteCharacter(id)
}
