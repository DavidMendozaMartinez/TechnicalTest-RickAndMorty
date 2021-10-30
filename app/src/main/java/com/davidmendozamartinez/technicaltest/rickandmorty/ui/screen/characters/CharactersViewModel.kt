package com.davidmendozamartinez.technicaltest.rickandmorty.ui.screen.characters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.model.Character
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.usecase.GetCharactersUseCase
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.usecase.GetFavoriteCharactersUseCase
import com.davidmendozamartinez.technicaltest.rickandmorty.ui.util.State
import com.davidmendozamartinez.technicaltest.rickandmorty.ui.util.State.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getFavoriteCharactersUseCase: GetFavoriteCharactersUseCase
) : ViewModel() {

    private val _state: MutableLiveData<State> = MutableLiveData(LOADING)
    val isLoadingStateVisible = Transformations.map(_state) { it == LOADING }
    val isEmptyStateVisible = Transformations.map(_state) { it == EMPTY }
    val isErrorStateVisible = Transformations.map(_state) { it == ERROR }
    val isSuccessStateVisible = Transformations.map(_state) { it == SUCCESS }

    private var characters: Flow<PagingData<Character>>? = null

    fun getCharacters(section: Section): Flow<PagingData<Character>> {
        return characters ?: requestCharacters(section)
            .cachedIn(viewModelScope)
            .also { characters = it }
    }

    private fun requestCharacters(section: Section): Flow<PagingData<Character>> = when (section) {
        Section.ALL -> getCharactersUseCase.invoke()
        Section.FAVORITES -> getFavoriteCharactersUseCase.invoke()
    }

    fun setState(state: State) {
        _state.value = state
    }
}
