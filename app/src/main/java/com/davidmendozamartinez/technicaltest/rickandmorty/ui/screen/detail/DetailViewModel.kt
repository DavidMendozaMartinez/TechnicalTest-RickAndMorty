package com.davidmendozamartinez.technicaltest.rickandmorty.ui.screen.detail

import androidx.lifecycle.*
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.model.Character
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.usecase.AddFavoriteCharacterUseCase
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.usecase.IsFavoriteCharacterUseCase
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.usecase.RemoveFavoriteCharacterUseCase
import com.davidmendozamartinez.technicaltest.rickandmorty.ui.util.State
import com.davidmendozamartinez.technicaltest.rickandmorty.ui.util.State.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val isFavoriteCharacterUseCase: IsFavoriteCharacterUseCase,
    private val addFavoriteCharacterUseCase: AddFavoriteCharacterUseCase,
    private val removeFavoriteCharacterUseCase: RemoveFavoriteCharacterUseCase
) : ViewModel() {
    private val _state: MutableLiveData<State> = MutableLiveData(LOADING)

    private val _character: MutableLiveData<Character> = MutableLiveData()
    val character: LiveData<Character> get() = _character

    private var _isFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    val isLoadingStateVisible = Transformations.map(_state) { it == LOADING }
    val isButtonAddToFavoritesVisible = Transformations.map(isFavorite) { it == false }
    val isButtonRemoveFromFavoritesVisible = Transformations.map(isFavorite) { it == true }

    fun onCharacterReceived(character: Character) {
        _character.value = character

        _state.value = LOADING
        viewModelScope.launch {
            try {
                _isFavorite.value = isFavoriteCharacterUseCase.invoke(character.id)
                _state.value = SUCCESS
            } catch (throwable: Throwable) {
                _state.value = ERROR
            }
        }
    }

    fun onAddToFavoritesButtonClicked(character: Character) {
        _state.value = LOADING

        viewModelScope.launch {
            addFavoriteCharacterUseCase.invoke(character)
            _isFavorite.value = true
            _state.value = SUCCESS
        }
    }

    fun onRemoveFromFavoritesButtonClicked(character: Character) {
        _state.value = LOADING

        viewModelScope.launch {
            removeFavoriteCharacterUseCase.invoke(character)
            _isFavorite.value = false
            _state.value = SUCCESS
        }
    }
}
