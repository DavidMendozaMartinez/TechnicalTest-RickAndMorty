package com.davidmendozamartinez.technicaltest.rickandmorty.ui.screen.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.davidmendozamartinez.technicaltest.rickandmorty.R
import com.davidmendozamartinez.technicaltest.rickandmorty.databinding.FragmentCharactersBinding
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.model.Character
import com.davidmendozamartinez.technicaltest.rickandmorty.ui.adapter.CharacterAdapter
import com.davidmendozamartinez.technicaltest.rickandmorty.ui.util.State
import com.davidmendozamartinez.technicaltest.rickandmorty.ui.util.State.*
import com.davidmendozamartinez.technicaltest.rickandmorty.ui.util.setStatusBarPadding
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharactersFragment : Fragment() {
    private lateinit var binding: FragmentCharactersBinding
    private lateinit var viewModel: CharactersViewModel
    private val args: CharactersFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough().apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[CharactersViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        setupList()
    }

    private fun setupList() {
        val adapter = CharacterAdapter { view, character -> navigateToDetail(view, character) }
        adapter.addLoadStateListener { loadState ->
            val state: State = when (loadState.source.refresh) {
                is LoadState.Loading -> LOADING
                is LoadState.Error -> ERROR
                is LoadState.NotLoading ->
                    if (loadState.append.endOfPaginationReached && adapter.itemCount == 0) EMPTY
                    else SUCCESS
            }
            viewModel.setState(state)
        }

        binding.listContainer.recyclerView.adapter = adapter
        binding.listContainer.recyclerView.setStatusBarPadding()
        binding.errorState.buttonRetry.setOnClickListener { adapter.retry() }

        lifecycleScope.launch {
            viewModel.getCharacters(args.section).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun navigateToDetail(view: View, character: Character) {
        exitTransition = MaterialElevationScale(false).apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }

        val detailsTransitionName = getString(R.string.detail_transition_name)
        val extras = FragmentNavigatorExtras(view to detailsTransitionName)
        findNavController().navigate(
            CharactersFragmentDirections.actionCharactersFragmentToDetailFragment(character),
            extras
        )
    }
}
