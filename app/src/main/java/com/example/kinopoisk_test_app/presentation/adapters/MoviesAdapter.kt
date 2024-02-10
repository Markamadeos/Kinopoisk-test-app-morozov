package com.example.kinopoisk_test_app.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoisk_test_app.databinding.MovieListItemBinding
import com.example.kinopoisk_test_app.domian.models.Movie
import com.example.kinopoisk_test_app.presentation.viewholders.MoviesViewHolder

class MoviesAdapter(
    private val onClick: (Movie) -> Unit,
    val onLongClick: (Movie) -> Boolean
) :
    RecyclerView.Adapter<MoviesViewHolder>() {
    private val items: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding =
            MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { onClick.invoke(item) }
        holder.itemView.setOnLongClickListener { onLongClick.invoke(item) }
    }

    fun addMovies(movies: List<Movie>) {
        items.addAll(movies)
        notifyDataSetChanged()
    }

    fun clearData() {
        items.clear()
        notifyDataSetChanged()
    }
}