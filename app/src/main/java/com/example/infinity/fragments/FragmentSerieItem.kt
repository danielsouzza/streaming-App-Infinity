package com.example.infinity.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.infinity.R
import com.example.infinity.activitys.YoutubeActivity
import com.example.infinity.adaptes.SerieAdapter
import com.example.infinity.model.Category
import com.example.infinity.model.Genres
import com.example.infinity.model.Movie
import com.example.infinity.model.Serie
import com.example.infinity.request.*

class FragmentSerieItem(private val serie: Serie) : Fragment(), CallBack {
    private lateinit var movieView: View
    private lateinit var progressBar: ProgressBar
    private lateinit var title: TextView
    private lateinit var desc: TextView
    private lateinit var rv : RecyclerView
    private lateinit var adapter: SerieAdapter
    private lateinit var suggestions:MutableList<Serie>
    private lateinit var btnWatch: Button


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


        SereTask(this).execute(Urls.getUrlSere(serie.id))
        VideoTask(this).execute(Urls.getUrlVideo(serie.id))
        adapter = SerieAdapter(this,suggestions, R.layout.similar_movies_item)
        rv.layoutManager = GridLayoutManager(context,2)
        rv.adapter = adapter

        btnWatch.setOnClickListener{
            val intent = Intent(context, YoutubeActivity::class.java)
            intent.putExtra("urlVideo",serie.urlVideo)
            startActivity(intent)
        }

    }

    override fun onPreExecute() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onResult(category: Category) {
        this.suggestions.addAll(category.series!!)
        this.adapter.notifyDataSetChanged()
    }

    override fun onResult(serie: Serie) {
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
        }).execute(Urls.getUrlsImg(serie.imgUrl))

        Log.e("Url",Urls.getUrlMovie(serie.id))

        title.text = serie.title
        desc.text = serie.overView

        progressBar.visibility = View.GONE
    }

    override fun onResult(genres: List<Genres>) {
        TODO("Not yet implemented")
    }

    override fun onResult(movie: Movie) {
        TODO("Not yet implemented")
    }

    override fun onResult(url: String) {
        serie.urlVideo = url
    }

    override fun onFailure(message: String) {
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
    }
}