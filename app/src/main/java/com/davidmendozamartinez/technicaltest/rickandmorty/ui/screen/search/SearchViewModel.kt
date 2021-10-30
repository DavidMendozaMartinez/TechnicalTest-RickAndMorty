package com.davidmendozamartinez.technicaltest.rickandmorty.ui.screen.search

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.model.Character
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.usecase.SearchCharactersUseCase
import com.davidmendozamartinez.technicaltest.rickandmorty.ui.util.State
import com.davidmendozamartinez.technicaltest.rickandmorty.ui.util.State.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchCharactersUseCase: SearchCharactersUseCase
) : ViewModel() {

    private val _state: MutableLiveData<State> = MutableLiveData(LOADING)
    val isLoadingStateVisible = Transformations.map(_state) { it == LOADING }
    val isEmptyStateVisible = Transformations.map(_state) { it == EMPTY }
    val isErrorStateVisible = Transformations.map(_state) { it == ERROR }
    val isSuccessStateVisible = Transformations.map(_state) { it == SUCCESS }

    private var _query: MutableLiveData<String> = MutableLiveData<String>("")
    val query: LiveData<String> get() = _query

    private var characters: Flow<PagingData<Character>>? = null

    fun searchCharacters(query: String): Flow<PagingData<Character>> {
        val lastResult = characters
        if (query == _query.value && lastResult != null) {
            return lastResult
        }

        _query.value = query

        return searchCharactersUseCase.invoke(query)
            .cachedIn(viewModelScope)
            .also { characters = it }
    }

    fun setState(state: State) {
        _state.value = state
    }
}
