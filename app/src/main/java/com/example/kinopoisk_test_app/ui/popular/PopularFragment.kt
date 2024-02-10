package com.example.kinopoisk_test_app.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kinopoisk_test_app.R
import com.example.kinopoisk_test_app.databinding.FragmentPopularBinding
import com.example.kinopoisk_test_app.domian.models.Movie
import com.example.kinopoisk_test_app.presentation.adapters.MoviesAdapter
import com.example.kinopoisk_test_app.presentation.models.PopularScreenState
import com.example.kinopoisk_test_app.presentation.viewModels.PopularViewModel
import com.example.kinopoisk_test_app.util.MOVIE_ID
import com.example.kinopoisk_test_app.util.debounce
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularFragment : Fragment() {
    private val viewModel by viewModel<PopularViewModel>()
    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!
    private var movieClickDebounce: ((Movie) -> Unit)? = null
    private val moviesAdapter = MoviesAdapter { movie ->
        movieClickDebounce?.let { movieClickDebounce -> movieClickDebounce(movie) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.screenState.observe(viewLifecycleOwner) {
            updateScreen(it)
        }
        setClickDebounce()
        bind()
    }

    private fun bind() {
        with(binding) {
            etSearchQuery.doAfterTextChanged { input ->
                viewModel.searchMovies(input.toString())
            }
            btnBack.setOnClickListener {
                tvHeader.isVisible = true
                etSearchQuery.isVisible = false
                btnSearch.isVisible = true
                btnBack.isVisible = false
            }
            btnSearch.setOnClickListener {
                tvHeader.isVisible = false
                etSearchQuery.isVisible = true
                btnSearch.isVisible = false
                btnBack.isVisible = true
            }
            rvFilms.adapter = moviesAdapter
        }
    }

    private fun setClickDebounce() {
        movieClickDebounce = debounce(
            CLICK_DEBOUNCE_DELAY_MILLIS,
            viewLifecycleOwner.lifecycleScope,
            false
        ) { movieItem ->
            val movieBundle = bundleOf(MOVIE_ID to movieItem.id)
            findNavController().navigate(
                R.id.action_popularFragment_to_detailFragment,
                movieBundle
            )
        }
    }

    private fun updateScreen(state: PopularScreenState) {
        when (state) {
            is PopularScreenState.Loading -> showLoading()
            is PopularScreenState.Content -> showContent()
            is PopularScreenState.Empty -> showEmptyState()
            is PopularScreenState.InternetError -> showInternetError()
            is PopularScreenState.ServerError -> showServerError()
        }
    }

    private fun showServerError() {
        TODO("Not yet implemented")
    }

    private fun showInternetError() {
        TODO("Not yet implemented")
    }

    private fun showEmptyState() {
        TODO("Not yet implemented")
    }

    private fun showContent() {
        TODO("Not yet implemented")
    }

    private fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 200L
    }
}