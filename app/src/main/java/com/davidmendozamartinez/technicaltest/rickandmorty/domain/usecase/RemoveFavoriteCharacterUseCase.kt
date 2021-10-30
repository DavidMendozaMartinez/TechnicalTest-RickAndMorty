package com.davidmendozamartinez.technicaltest.rickandmorty.domain.usecase

import com.davidmendozamartinez.technicaltest.rickandmorty.domain.model.Character
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.repository.CharacterRepository

class RemoveFavoriteCharacterUseCase(private val repository: CharacterRepository) {
    suspend fun invoke(character: Character): Unit = repository.removeFavoriteCharacter(character)
}
