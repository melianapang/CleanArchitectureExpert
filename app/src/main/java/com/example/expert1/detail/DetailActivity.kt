package com.example.expert1.detail

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.core.domain.model.Movie
import com.example.core.domain.model.Show
import com.example.core.utils.MappingHelper
import com.example.core.valueobject.Status
import com.example.expert1.R
import com.example.expert1.databinding.ActivityDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detViewModel: DetailViewModel by viewModel()

    companion object {
        const val EXTRA_NAME = "extra_name_film"
        const val EXTRA_FRAGMENT = "extra_fragment"
        const val EXTRA_FILM_ID = "extra_film_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(true)

        val bundle = intent.extras
        if (bundle != null) {
            val filmId = bundle.getInt(EXTRA_FILM_ID)
            val filmStr = bundle.getString(EXTRA_NAME)
            when (bundle.getString(EXTRA_FRAGMENT)) {
                "MOVIES" -> {
                    if (filmStr != null) {
                        detViewModel.setSelectedMovie(filmId)
                        detViewModel.getSelectedMovie().observe(this, { selectedMovie ->
                            if (selectedMovie != null) {
                                when (selectedMovie.status) {
                                    Status.LOADING -> showLoading(true)
                                    Status.SUCCESS -> if (selectedMovie.data != null) {
                                        showLoading(true)
                                        setSupportBar(filmStr)
                                        manageData(selectedMovie.data, null)
                                        showLoading(false)
                                    }
                                    Status.ERROR -> {
                                        showLoading(false)
                                    }
                                }
                            } else {
                                showLoading(false)
                            }
                        })
                    }
                }
                "TV SHOWS" -> {
                    if (filmStr != null) {
                        detViewModel.setSelectedTvShow(filmId)
                        detViewModel.getSelectedTvShow().observe(this, { selectedShow ->
                            if (selectedShow != null) {
                                when (selectedShow.status) {
                                    Status.LOADING -> showLoading(true)
                                    Status.SUCCESS -> if (selectedShow.data != null) {
                                        setSupportBar(filmStr)
                                        manageData(null, selectedShow.data)
                                        showLoading(false)
                                    }
                                    Status.ERROR -> {
                                        showLoading(false)
                                    }
                                }
                            } else {
                                showLoading(false)
                            }
                        })
                    }
                }
            }

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setSupportBar(movieName: String) {
        supportActionBar?.apply {
            customView = setTitleBar(movieName)
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setTitleBar(movieName: String): TextView {
        return TextView(this).apply {
            text = movieName

            val params = ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT
            )
            layoutParams = params
            params.gravity = Gravity.START

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setTextAppearance(
                    android.R.style.TextAppearance_Material_Widget_ActionBar_Title
                )
            } else {
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
                setTypeface(null, Typeface.BOLD)
            }
            setTextColor(Color.WHITE)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun manageData(movie: Movie?, show: Show?) {
        with(binding) {
            tvJudulDetail.text = movie?.title ?: show?.title
            tvOverviewContent.text = movie?.overview ?: show?.overview
            tvDuration.text = resources.getQuantityString(
                R.plurals.durations,
                movie?.runTime ?: show?.runTime ?: 0,
                movie?.runTime ?: show?.runTime
            )

            val dateFormatBefore = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val yearFormatAfter = SimpleDateFormat("yyyy", Locale.getDefault())
            val dateFormatAfter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val dateFormat = dateFormatBefore.parse(movie?.releaseDate ?: show?.firstAirDate)

            val yearForm = yearFormatAfter.format(dateFormat)
            val dateForm = dateFormatAfter.format(dateFormat)
            tvTahunDetail.text = yearForm
            tvDateDetail.text = dateForm

            tvGenre.text = movie?.genres ?: show?.genres

            if (show != null) {
                tvEpisodes.visibility = View.VISIBLE
                tvEpisodes.text = resources.getQuantityString(
                    R.plurals.numOfEps,
                        show.numOfEps,
                        show.numOfEps
                )

                tvSeasons.visibility = View.VISIBLE
                tvSeasons.text = resources.getQuantityString(
                    R.plurals.numOfSeasons,
                        show.numOfSeasons,
                        show.numOfSeasons
                )

                if(show.isFavorite == 0) showFavorite(false) else showFavorite(true)
            }
            else{
                if(movie?.isFavorite == 0) showFavorite(false) else showFavorite(true)
            }

            btnShare.setOnClickListener { onShareClick(movie, show) }
            btnFavorite.setOnClickListener{ onSetFavorite(movie, show) }

            val poster = MappingHelper.mapPosterPath(movie?.posterPath ?: show?.posterPath)
            Glide.with(root)
                .load(poster)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(imgPosterDetail)
        }
    }

    private fun onSetFavorite(movie: Movie?, show: Show?) {
        if (movie != null) {
            when(movie.isFavorite) {
               1 -> {
                   detViewModel.deleteFavoriteMovie(movie.id)
                   showToast("${movie.title} ${getString(R.string.remove_favorite)}")
                   showFavorite(false)
               }
                0-> {
                    detViewModel.setFavoriteMovie(movie.id)
                    showToast("${movie.title} ${getString(R.string.add_favorite)}")
                    showFavorite(true)
                }
            }
        } else if (show != null) {
            when(show.isFavorite) {
                1 -> {
                    detViewModel.deleteFavoriteShow(show.id)
                    showToast("${show.title} ${getString(R.string.remove_favorite)}")
                    showFavorite(false)
                }
                0 -> {
                    detViewModel.setFavoriteShow(show.id)
                    showToast("${show.title} ${getString(R.string.add_favorite)}")
                    showFavorite(true)
                }
            }
        }
    }

    private fun onShareClick(movie: Movie?, show: Show?) {
        val mimeType = "text/plain"
        ShareCompat.IntentBuilder
            .from(this@DetailActivity)
            .setType(mimeType)
            .setChooserTitle(resources.getString(R.string.share_title))
            .setText(resources.getString(R.string.share_text, movie?.title ?: show?.title))
            .startChooser()
    }

    @SuppressLint("ResourceAsColor")
    private fun showFavorite(state: Boolean) {
        if (state) {
            binding.btnFavorite.text = resources.getString(R.string.delete_fav)
        } else {
            binding.btnFavorite.text = resources.getString(R.string.add_fav)
        }
    }

    private fun showToast(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.INVISIBLE
    }
}