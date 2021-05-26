package com.example.core.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.core.R
import com.example.core.databinding.FilmItemBinding
import com.example.core.domain.model.Movie
import com.example.core.utils.MappingHelper
import java.text.SimpleDateFormat
import java.util.*

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private var listData: ArrayList<Movie> = ArrayList()

    companion object {
        private const val MOVIE_TYPE = "MOVIES"
    }

    fun setData(newListData: List<Movie>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = FilmItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = listData[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = listData.size

    inner class MovieViewHolder(private val binding: FilmItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: Movie) {
            with(binding) {
                tvJudul.text = film.title
                val dateFormatBefore = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val dateFormatAfter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                val dateForm = dateFormatBefore.parse(film.releaseDate)
                val dateText = dateFormatAfter.format(dateForm)
                tvDate.text = dateText

                val poster = MappingHelper.mapPosterPath(film.posterPath)
                Glide.with(itemView.context)
                    .load(poster)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgPoster)
            }

            itemView.setOnClickListener { onItemClickCallback?.onItemClicked(film, MOVIE_TYPE) }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Movie, type: String)
    }

}