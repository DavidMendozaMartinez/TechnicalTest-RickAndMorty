package com.davidmendozamartinez.technicaltest.rickandmorty.ui.screen.search

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionListenerAdapter
import com.davidmendozamartinez.technicaltest.rickandmorty.R
import com.davidmendozamartinez.technicaltest.rickandmorty.databinding.FragmentSearchBinding
import com.davidmendozamartinez.technicaltest.rickandmorty.domain.model.Character
import com.davidmendozamartinez.technicaltest.rickandmorty.ui.adapter.CharacterAdapter
import com.davidmendozamartinez.technicaltest.rickandmorty.ui.util.*
import com.davidmendozamartinez.technicaltest.rickandmorty.ui.util.State.*
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: CharacterAdapter

    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        enterTransition = MaterialContainerTransform().apply {
            startView = requireActivity().findViewById(R.id.fab)
            endView = binding.container
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
            scrimColor = Color.TRANSPARENT
            containerColor = requireContext().themeColor(R.attr.colorSurface)
            startContainerColor = requireContext().themeColor(R.attr.colorSecondary)
            endContainerColor = requireContext().themeColor(R.attr.colorSurface)
            addListener(object : TransitionListenerAdapter() {
                override fun onTransitionEnd(transition: Transition) {
                    binding.editTextSearch.showIme()
                }
            })
        }

        returnTransition = Slide().apply {
            duration = resources.getInteger(R.integer.motion_duration_medium).toLong()
            addTarget(R.id.container)
        }

        setupList()
        setupSearcher()

        viewModel.query.observe(viewLifecycleOwner) {
            binding.editTextSearch.hideIme()
            if (it.isNotEmpty()) searchCharacters(it)
        }
    }

    private fun setupList() {
        adapter = CharacterAdapter { _, id -> navigateToDetail(id) }
        adapter.addLoadStateListener { loadState ->
            val state: State = when (loadState.source.refresh) {
                is LoadState.Loading -> LOADING
                is LoadState.Error -> EMPTY
                is LoadState.NotLoading ->
                    if (loadState.append.endOfPaginationReached && adapter.itemCount == 0) EMPTY
                    else SUCCESS
            }
            viewModel.setState(state)
        }

        binding.listContainer.recyclerView.adapter = adapter
        binding.errorState.buttonRetry.setOnClickListener { adapter.retry() }
    }

    private fun setupSearcher() {
        binding.container.setStatusBarPadding()
        binding.toolbar.setNavigationOnClickListener {
            navigateUp()
            binding.editTextSearch.hideIme()
        }

        with(binding.editTextSearch) {
            setOnEditorActionListener { view, actionId, _ ->
                (EditorInfo.IME_ACTION_SEARCH == actionId).also { isActionSearch ->
                    if (isActionSearch) {
                        searchCharacters(view.text.toString())
                        view.hideIme()
                    }
                }
            }
        }
    }

    private fun searchCharacters(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchCharacters(query.trim()).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun navigateToDetail(character: Character) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailFragment(character)
        )
    }

    private fun navigateUp() {
        findNavController().navigateUp()
    }
}
