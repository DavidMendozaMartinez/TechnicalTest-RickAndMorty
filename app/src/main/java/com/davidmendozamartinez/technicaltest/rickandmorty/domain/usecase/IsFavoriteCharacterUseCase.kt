package com.davidmendozamartinez.technicaltest.rickandmorty.domain.usecase

import com.davidmendozamartinez.technicaltest.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

class IsFavoriteCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend fun invoke(id: Int): Boolean = repository.isFavoriteCharacter(id)
}
