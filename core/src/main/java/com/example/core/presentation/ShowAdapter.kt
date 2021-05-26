package com.example.core.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.core.R
import com.example.core.databinding.FilmItemBinding
import com.example.core.domain.model.Show
import com.example.core.utils.MappingHelper
import java.text.SimpleDateFormat
import java.util.*

class ShowAdapter : RecyclerView.Adapter<ShowAdapter.ShowViewHolder>() {
    private var listData = ArrayList<Show>()

    companion object {
        private const val MOVIE_TYPE = "TV SHOWS"
    }

    fun setData(newListData: List<Show>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val binding = FilmItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        val show = listData[position]
        holder.bind(show)
    }

    override fun getItemCount(): Int = listData.size

    inner class ShowViewHolder(private val binding: FilmItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: Show) {
            with(binding) {
                tvJudul.text = film.title
                val dateFormatBefore = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val dateFormatAfter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                val dateForm = dateFormatBefore.parse(film.firstAirDate)
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
        fun onItemClicked(data: Show, type: String)
    }

}