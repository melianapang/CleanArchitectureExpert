package com.example.favorite.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.core.domain.model.Movie
import com.example.core.presentation.MovieAdapter
import com.example.expert1.detail.DetailActivity
import com.example.favorite.FavoriteViewModel
import com.example.favorite.databinding.FragmentFavoriteMovieBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteMovieBinding
    private lateinit var rvAdapter: MovieAdapter
    private val favViewModel: FavoriteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteMovieBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading(true)

        rvAdapter = MovieAdapter()

        initialiseRecyclerView()
        generateDataShow()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rvAdapter.setOnItemClickCallback(object : MovieAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Movie, type: String) {
                val intent = Intent(requireActivity(), Class.forName("com.example.expert1.detail.DetailActivity"))
                intent.putExtra(DetailActivity.EXTRA_FRAGMENT, type)
                intent.putExtra(DetailActivity.EXTRA_NAME, data.title)
                intent.putExtra(DetailActivity.EXTRA_FILM_ID, data.id)
                startActivity(intent)
            }
        })
    }

    private fun initialiseRecyclerView() {
        with(binding.rvFavorite) {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 2)
            adapter = rvAdapter
        }
    }

    private fun generateDataShow() {
        with(binding.rvFavorite) {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = rvAdapter
        }

        favViewModel.getFavoriteFilm().observe(viewLifecycleOwner, { listFilm ->
            if (listFilm != null) {
                rvAdapter.setData(listFilm)
                rvAdapter.notifyDataSetChanged()
                showLoading(false)
            }
            else{
                showLoading(true)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        binding.progressBarFavorite.visibility = if (state) View.VISIBLE else View.INVISIBLE
    }
}