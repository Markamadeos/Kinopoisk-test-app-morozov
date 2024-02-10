package com.example.kinopoisk_test_app.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kinopoisk_test_app.R
import com.example.kinopoisk_test_app.databinding.FragmentFavoriteBinding
import com.example.kinopoisk_test_app.domian.models.Movie
import com.example.kinopoisk_test_app.presentation.adapters.MoviesAdapter
import com.example.kinopoisk_test_app.presentation.models.FavoriteScreenState
import com.example.kinopoisk_test_app.presentation.viewModels.FavoriteViewModel
import com.example.kinopoisk_test_app.util.MOVIE_ID
import com.example.kinopoisk_test_app.util.debounce
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<FavoriteViewModel>()
    private var movieClickDebounce: ((Movie) -> Unit)? = null
    private val moviesAdapter = MoviesAdapter(
        { movie ->
            movieClickDebounce?.let { movieClickDebounce -> movieClickDebounce(movie) }
        },
        { onMovieLongClick(it) }
    )

    private fun onMovieLongClick(movie: Movie): Boolean {
        viewModel.deleteMovieFromFavorites(movie)
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.screenState.observe(viewLifecycleOwner) {
            updateScreen(it)
        }
        viewModel.getMovies()
        binding.rvFilms.adapter = moviesAdapter
    }

    private fun updateScreen(state: FavoriteScreenState) {
        when (state) {
            is FavoriteScreenState.Loading -> showLoading()
            is FavoriteScreenState.Content -> showContent(state.movies)
            is FavoriteScreenState.Empty -> showEmptyState(state.message)
        }
    }

    private fun showEmptyState(message: Int) {
        with(binding) {
            rvFilms.isVisible = false
            pbLoading.isVisible = false
            tvEmptyPlaceholder.isVisible = true
            tvEmptyPlaceholder.text = getString(message)
        }
    }

    private fun showContent(movies: List<Movie>) {
        with(binding) {
            rvFilms.isVisible = true
            pbLoading.isVisible = false
            tvEmptyPlaceholder.isVisible = false
            moviesAdapter.clearData()
            moviesAdapter.addMovies(movies)
        }
    }

    private fun showLoading() {
        with(binding) {
            rvFilms.isVisible = false
            pbLoading.isVisible = true
            tvEmptyPlaceholder.isVisible = false
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
                R.id.action_favoriteFragment_to_detailFragment,
                movieBundle
            )
        }
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 200L
    }
}