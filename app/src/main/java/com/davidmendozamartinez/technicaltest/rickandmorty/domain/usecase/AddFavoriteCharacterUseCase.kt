package com.davidmendozamartinez.technicaltest.rickandmorty.domain.usecase

import com.davidmendozamartinez.technicaltest.rickandmorty.domain.model.Character
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.repository.CharacterRepository

class AddFavoriteCharacterUseCase(private val repository: CharacterRepository) {
    suspend fun invoke(character: Character): Unit = repository.addFavoriteCharacter(character)
}
