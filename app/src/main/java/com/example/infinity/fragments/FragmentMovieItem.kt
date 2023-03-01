package com.example.infinity.fragments

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.widget.Toolbar
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.infinity.adaptes.MovieAdapter
import com.example.infinity.R
import com.example.infinity.activitys.YoutubeActivity
import com.example.infinity.model.Category
import com.example.infinity.model.Genres
import com.example.infinity.model.Movie
import com.example.infinity.model.Serie
import com.example.infinity.request.*

class FragmentMovieItem(private val movie: Movie) : Fragment(), CallBack {
    private lateinit var movieView: View
    private lateinit var progressBar: ProgressBar
    private lateinit var title: TextView
    private lateinit var desc: TextView
    private lateinit var rv : RecyclerView
    private lateinit var adapter: MovieAdapter
    private lateinit var suggestions:MutableList<Movie>
    private lateinit var btnWatch: Button
    private lateinit var toolbar: Toolbar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_template_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieView = view
        suggestions = mutableListOf()
        title = view.findViewById(R.id.title_movie)
        desc =  view.findViewById(R.id.txt_desc)
        rv = view.findViewById(R.id.rv_suggestion)
        progressBar = view.findViewById(R.id.progress_movie)
        btnWatch = view.findViewById(R.id.btn_watch)

//        toolbar = view.findViewById(R.id.movie_toolbar)






        MovieTask(this).execute(Urls.getUrlMovie(movie.id))
        VideoTask(this).execute(Urls.getUrlVideo(movie.id))
        adapter = MovieAdapter(this,suggestions, R.layout.similar_movies_item)
        rv.layoutManager = GridLayoutManager(context,2)
        rv.adapter = adapter

        btnWatch.setOnClickListener{
            val intent = Intent(context, YoutubeActivity::class.java)
            intent.putExtra("urlVideo",movie.urlVideo)
            startActivity(intent)
        }

    }

    override fun onPreExecute() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onResult(category: Category) {
        this.suggestions.addAll(category.movies!!)
        this.adapter.notifyDataSetChanged()
    }

    override fun onResult(movie: Movie) {
        progressBar.visibility = View.GONE

        DownloadImgTask(object : DownloadImgTask.Callback{
            override fun onResult(bitmap: Bitmap) {
                val layerDrawable: LayerDrawable = ContextCompat.getDrawable(requireContext(),
                    R.drawable.shadow_banner
                ) as LayerDrawable
                val movieCover = BitmapDrawable(resources,bitmap)
                layerDrawable.setDrawableByLayerId(R.id.cover_drawable,movieCover)
                val coverImg: ImageView = movieView.findViewById(R.id.img_cover)
                coverImg.setImageDrawable(layerDrawable)
            }
        }).execute(Urls.getUrlsImg(movie.imgUrl))

        Log.e("Url",Urls.getUrlMovie(movie.id))

        title.text = movie.title
        desc.text = movie.overView

        progressBar.visibility = View.GONE
    }

    override fun onResult(genres: List<Genres>) {
        TODO("Not yet implemented")
    }

    override fun onResult(sere: Serie) {
        TODO("Not yet implemented")
    }

    override fun onResult(url: String) {
        movie.urlVideo = url
    }

    override fun onFailure(message: String) {
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
    }
}