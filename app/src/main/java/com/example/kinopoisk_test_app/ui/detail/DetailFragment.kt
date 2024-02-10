package com.example.kinopoisk_test_app.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.kinopoisk_test_app.R
import com.example.kinopoisk_test_app.databinding.FragmentDetailBinding
import com.example.kinopoisk_test_app.domian.models.Movie
import com.example.kinopoisk_test_app.presentation.models.DetailScreenState
import com.example.kinopoisk_test_app.presentation.viewModels.DetailsViewModel
import com.example.kinopoisk_test_app.util.MOVIE_ID
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {
    private val viewModel by viewModel<DetailsViewModel>()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var movieId = EMPTY_ID

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.screenState.observe(viewLifecycleOwner) {
            updateScreen(it)
        }
        bind()
        movieId = requireArguments().getString(MOVIE_ID) ?: EMPTY_ID
        viewModel.fillData(movieId)
    }

    private fun bind() {
        with(binding) {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun updateScreen(state: DetailScreenState) {
        when (state) {
            is DetailScreenState.Loading -> showLoading()
            is DetailScreenState.Content -> showContent(state.movie)
            is DetailScreenState.InternetError -> this.showErrorState(state.message)
            is DetailScreenState.ServerError -> this.showErrorState(state.message)
        }
    }

    private fun showErrorState(message: Int) {
        with(binding) {
            bsMovieDescription.isVisible = false
            ivCover.isVisible = false
            tvInternetError.isVisible = true
            ivInternetError.isVisible = true
            pbLoading.isVisible = false
            tvInternetError.text = getString(message)
        }
    }

    private fun showContent(movie: Movie) {
        with(binding) {
            bsMovieDescription.isVisible = true
            ivCover.isVisible = true
            tvInternetError.isVisible = false
            ivInternetError.isVisible = false
            pbLoading.isVisible = false

            tvMovieTitle.text = movie.name
            tvDescription.text = movie.description
            tvCountryValue.text = movie.countries
            tvGenreValue.text = movie.genres
            Glide.with(requireContext())
                .load(movie.cover)
                .transform(CenterCrop())
                .into(ivCover)
        }
    }

    private fun showLoading() {
        with(binding) {
            bsMovieDescription.isVisible = false
            ivCover.isVisible = false
            tvInternetError.isVisible = false
            ivInternetError.isVisible = false
            pbLoading.isVisible = true
        }
    }

    companion object {
        private const val EMPTY_ID = ""
    }
}