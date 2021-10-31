package com.davidmendozamartinez.technicaltest.rickandmorty.ui.screen.characters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.davidmendozamartinez.technicaltest.rickandmorty.MainCoroutineRule
import com.davidmendozamartinez.technicaltest.rickandmorty.data.repository.FakeCharacterRepositoryImpl
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.usecase.GetCharactersUseCase
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.usecase.GetFavoriteCharactersUseCase
import com.davidmendozamartinez.technicaltest.rickandmorty.getOrAwaitValue
import com.davidmendozamartinez.technicaltest.rickandmorty.ui.util.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CharactersViewModelTest {
    private lateinit var viewModel: CharactersViewModel

    private val repository = FakeCharacterRepositoryImpl()

    private val getCharactersUseCase = GetCharactersUseCase(repository)
    private val getFavoriteCharactersUseCase = GetFavoriteCharactersUseCase(repository)

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        viewModel = CharactersViewModel(
            getCharactersUseCase,
            getFavoriteCharactersUseCase
        )
    }

    @Test
    fun `When the characters are loading the progressBar is visible`() {
        viewModel.setState(State.LOADING)

        val isLoadingStateVisible = viewModel.isLoadingStateVisible.getOrAwaitValue()
        val isEmptyStateVisible = viewModel.isEmptyStateVisible.getOrAwaitValue()
        val isSuccessStateVisible = viewModel.isSuccessStateVisible.getOrAwaitValue()
        val isErrorStateVisible = viewModel.isErrorStateVisible.getOrAwaitValue()

        assertThat(isLoadingStateVisible, `is`(true))
        assertThat(isEmptyStateVisible, `is`(false))
        assertThat(isSuccessStateVisible, `is`(false))
        assertThat(isErrorStateVisible, `is`(false))
    }

    @Test
    fun `When empty character list has been received the empty state is visible`() {
        viewModel.setState(State.EMPTY)

        val isLoadingStateVisible = viewModel.isLoadingStateVisible.getOrAwaitValue()
        val isEmptyStateVisible = viewModel.isEmptyStateVisible.getOrAwaitValue()
        val isSuccessStateVisible = viewModel.isSuccessStateVisible.getOrAwaitValue()
        val isErrorStateVisible = viewModel.isErrorStateVisible.getOrAwaitValue()

        assertThat(isLoadingStateVisible, `is`(false))
        assertThat(isEmptyStateVisible, `is`(true))
        assertThat(isSuccessStateVisible, `is`(false))
        assertThat(isErrorStateVisible, `is`(false))
    }

    @Test
    fun `When characters have been received the list is visible`() {
        viewModel.setState(State.SUCCESS)

        val isLoadingStateVisible = viewModel.isLoadingStateVisible.getOrAwaitValue()
        val isEmptyStateVisible = viewModel.isEmptyStateVisible.getOrAwaitValue()
        val isSuccessStateVisible = viewModel.isSuccessStateVisible.getOrAwaitValue()
        val isErrorStateVisible = viewModel.isErrorStateVisible.getOrAwaitValue()

        assertThat(isLoadingStateVisible, `is`(false))
        assertThat(isEmptyStateVisible, `is`(false))
        assertThat(isSuccessStateVisible, `is`(true))
        assertThat(isErrorStateVisible, `is`(false))
    }

    @Test
    fun `When an error has occurred the error state is visible`() {
        viewModel.setState(State.ERROR)

        val isLoadingStateVisible = viewModel.isLoadingStateVisible.getOrAwaitValue()
        val isEmptyStateVisible = viewModel.isEmptyStateVisible.getOrAwaitValue()
        val isSuccessStateVisible = viewModel.isSuccessStateVisible.getOrAwaitValue()
        val isErrorStateVisible = viewModel.isErrorStateVisible.getOrAwaitValue()

        assertThat(isLoadingStateVisible, `is`(false))
        assertThat(isEmptyStateVisible, `is`(false))
        assertThat(isSuccessStateVisible, `is`(false))
        assertThat(isErrorStateVisible, `is`(true))
    }
}
