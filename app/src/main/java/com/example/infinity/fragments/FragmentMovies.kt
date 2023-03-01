package com.example.infinity.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.infinity.R
import com.example.infinity.adaptes.MovieAdapter
import com.example.infinity.model.Category
import com.example.infinity.model.Genres
import com.example.infinity.model.Movie
import com.example.infinity.model.Serie
import com.example.infinity.request.CallBack
import com.example.infinity.request.CategoryTask
import com.example.infinity.request.GenresListTask
import com.example.infinity.request.Urls


class FragmentMovies : Fragment(),CallBack {
    private lateinit var adapter: MovieAdapter
    private lateinit var movies:MutableList<Movie>
    private lateinit var genres: MutableList<Genres>
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        GenresListTask(this).execute(Urls.getUrlGenres("movie"))
        genres = mutableListOf()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view.findViewById(R.id.progress_movies)
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_movies)


        movies = mutableListOf()

        adapter = MovieAdapter(this,movies,R.layout.similar_movies_item)
        recyclerView.layoutManager = GridLayoutManager(context,3)
        recyclerView.adapter = adapter
    }

    override fun onPreExecute() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onResult(category: Category) {
        progressBar.visibility = View.GONE
        this.movies.addAll(category.movies!!)
        this.adapter.notifyDataSetChanged()
    }

    override fun onResult(movie: Movie) {
        TODO("Not yet implemented")
    }

    override fun onResult(genres: List<Genres>) {
        this.genres.addAll(genres)
        val teste = genres[0]
        CategoryTask("movie",this).execute(Urls.getUrlCategoryByGenres("movie",teste.id))
    }

    override fun onResult(sere: Serie) {
        TODO("Not yet implemented")
    }

    override fun onResult(url: String) {
        TODO("Not yet implemented")
    }

    override fun onFailure(message: String) {
        TODO("Not yet implemented")
    }
}