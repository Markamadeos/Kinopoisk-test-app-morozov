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
import com.example.kinopoisk_test_app.util.hideKeyboard
import com.example.kinopoisk_test_app.util.showKeyboard
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularFragment : Fragment() {
    private val viewModel by viewModel<PopularViewModel>()
    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!
    private var movieClickDebounce: ((Movie) -> Unit)? = null

    private val moviesAdapter = MoviesAdapter(
        { movie ->
            movieClickDebounce?.let { movieClickDebounce -> movieClickDebounce(movie) }
        },
        { onMovieLongClick(it) }
    )
    private var currentQuery = EMPTY_QUERY

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
        viewModel.favoriteNotificationState.observe(viewLifecycleOwner) {
            showNotification(it)
        }
        setClickDebounce()
        bind()
        viewModel.getPopularMovies()
    }

    private fun showNotification(message: Int) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun onMovieLongClick(movie: Movie): Boolean {
        viewModel.saveMovieToDb(movie)
        moviesAdapter.updateItem(movie)
        return true
    }

    private fun bind() {
        with(binding) {
            etSearchQuery.doAfterTextChanged { input ->
                currentQuery = input.toString()
                viewModel.debounceSearch(currentQuery)
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
                etSearchQuery.requestFocus()
                showKeyboard()
            }
            btnInternetError.setOnClickListener {
                if (currentQuery.isEmpty()) {
                    viewModel.getPopularMovies()
                } else {
                    viewModel.searchMovies(currentQuery)
                }
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
            is PopularScreenState.Content -> showContent(state.movies)
            is PopularScreenState.Empty -> showEmptyState(state.message)
            is PopularScreenState.InternetError -> showInternetError(state.message)
            is PopularScreenState.ServerError -> showServerError(state.message)
        }
    }

    private fun showServerError(message: Int) {
        with(binding) {
            pbLoading.isVisible = false
            tvInternetError.isVisible = true
            tvServerError.isVisible = false
            rvFilms.isVisible = false
            ivInternetError.isVisible = true
            btnInternetError.isVisible = true
            tvInternetError.text = getString(message)
        }
    }

    private fun showInternetError(message: Int) {
        with(binding) {
            pbLoading.isVisible = false
            tvInternetError.isVisible = true
            tvServerError.isVisible = false
            rvFilms.isVisible = false
            ivInternetError.isVisible = true
            btnInternetError.isVisible = true
            tvInternetError.text = getString(message)
        }
    }

    private fun showEmptyState(message: Int) {
        with(binding) {
            pbLoading.isVisible = false
            tvInternetError.isVisible = false
            tvServerError.isVisible = true
            rvFilms.isVisible = false
            ivInternetError.isVisible = false
            btnInternetError.isVisible = false
            tvServerError.text = getString(message)
        }
    }

    private fun showContent(movies: List<Movie>) {
        with(binding) {
            hideKeyboard()
            pbLoading.isVisible = false
            tvInternetError.isVisible = false
            tvServerError.isVisible = false
            rvFilms.isVisible = true
            ivInternetError.isVisible = false
            btnInternetError.isVisible = false
            moviesAdapter.clearData()
            moviesAdapter.addMovies(movies)
        }
    }

    private fun showLoading() {
        with(binding) {
            pbLoading.isVisible = true
            tvInternetError.isVisible = false
            tvServerError.isVisible = false
            rvFilms.isVisible = false
            ivInternetError.isVisible = false
            btnInternetError.isVisible = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 200L
        private const val EMPTY_QUERY = ""
    }
}