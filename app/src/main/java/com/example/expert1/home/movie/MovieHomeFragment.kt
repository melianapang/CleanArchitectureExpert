package com.example.expert1.home.movie

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.core.domain.model.Movie
import com.example.expert1.R
import com.example.core.presentation.MovieAdapter
import com.example.expert1.databinding.FragmentHomeMovieBinding
import com.example.expert1.detail.DetailActivity
import com.example.expert1.home.HomeViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MovieHomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeMovieBinding
    private lateinit var rvAdapter: MovieAdapter
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeMovieBinding.inflate(layoutInflater)
        showLoading(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvAdapter = MovieAdapter()

        initialiseRecyclerView()
        generateData()
        searchViewManager()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        searchViewManager()

        rvAdapter.setOnItemClickCallback(object : MovieAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Movie, type: String) {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_FRAGMENT, type)
                intent.putExtra(DetailActivity.EXTRA_NAME, data.title)
                intent.putExtra(DetailActivity.EXTRA_FILM_ID, data.id)
                startActivity(intent)
            }
        })
    }

    private fun searchViewManager() {
        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding.searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        binding.searchView.queryHint = resources.getString(R.string.search_hint)
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showLoading(true)
                val queryName = StringBuilder().append("%").append(query).append("%").toString()
                homeViewModel.searchFilm(queryName).observe(viewLifecycleOwner, { films ->
                    rvAdapter.setData(films)
                    rvAdapter.notifyDataSetChanged()
                    if (films.isEmpty()) {
                        binding.tvSearchResult.visibility = View.VISIBLE
                    } else {
                        binding.tvSearchResult.visibility = View.INVISIBLE
                    }
                    showLoading(false)
                })
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                showLoading(true)
                val queryName = StringBuilder().append("%").append(newText).append("%").toString()
                homeViewModel.searchFilm(queryName).observe(viewLifecycleOwner, { films ->
                    rvAdapter.setData(films)
                    rvAdapter.notifyDataSetChanged()
                    if (films.isEmpty()) {
                        binding.tvSearchResult.visibility = View.VISIBLE
                    } else {
                        binding.tvSearchResult.visibility = View.INVISIBLE
                    }
                    showLoading(false)
                })
                return false
            }
        })
    }

    private fun initialiseRecyclerView() {
        with(binding.rvHome) {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 2)
            adapter = rvAdapter
        }
    }

    private fun generateData() {
        homeViewModel.getFilm().observe(this, { listFilm ->
            showLoading(true)
            if (listFilm.message != null) {
                showLoading(false)
                showToast(listFilm.message!!)
            } else {
                rvAdapter.setData(listFilm.data)
                rvAdapter.notifyDataSetChanged()
                showLoading(false)
            }
        })
    }

    private fun showToast(msg: String){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.INVISIBLE
    }
}