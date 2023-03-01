package com.example.infinity.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.infinity.adaptes.CategoryAdapter
import com.example.infinity.R
import com.example.infinity.adaptes.ViewPageAdapter
import com.example.infinity.model.Category
import com.example.infinity.model.Genres
import com.example.infinity.model.Movie
import com.example.infinity.model.Serie
import com.example.infinity.request.CallBack
import com.example.infinity.request.CategoryTask
import com.example.infinity.request.Urls
import java.lang.Math.abs

class FragmentHome : Fragment(),CallBack {
    private lateinit var viewPagerAdapter: ViewPageAdapter
    private lateinit var  viewPager:ViewPager2
    private lateinit var handler: Handler
    private lateinit var progressBar: ProgressBar
    private lateinit var bottomAdapter: CategoryAdapter
    private lateinit var bestMovies: MutableList<Movie>
    private lateinit var categories: MutableList<Category>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = view.findViewById(R.id.progress_home)
        categories = mutableListOf()
        bestMovies = mutableListOf()

        val rvMain : RecyclerView = view.findViewById(R.id.rv_main)

        // start view page
        viewPager = view.findViewById(R.id.vw_bestMovie)
        handler = Handler(Looper.myLooper()!!)
        viewPagerAdapter = ViewPageAdapter(this,bestMovies,viewPager)
        viewPager.adapter = viewPagerAdapter
        viewPager.offscreenPageLimit = 3
        viewPager.clipToPadding = false
        viewPager.clipChildren = false
        viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        //Animation view page scroll
        setUpTransformer()

        bottomAdapter = CategoryAdapter(this,categories)
        rvMain.layoutManager = LinearLayoutManager(context)
        rvMain.adapter = bottomAdapter

        val categoryTask = CategoryTask(type="movie",this)
        categoryTask.execute(Urls.getUrlCategory(0),resources.getString(R.string.popular))
        categoryTask.execute(Urls.getUrlCategory(1),resources.getString(R.string.now_playing))
        categoryTask.execute(Urls.getUrlCategory(2),resources.getString(R.string.upcoming))
        categoryTask.execute(Urls.getUrlCategory(3),resources.getString(R.string.top_rated))

    }


    private fun setUpTransformer() {
       val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(0))
        transformer.addTransformer{page,position ->
            val r = 1 - abs(position)
            page.scaleY = 0.90f + r * 0.14f
        }

        viewPager.setPageTransformer(transformer)
    }

    override fun onPreExecute() {
        progressBar.visibility = View.VISIBLE
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResult(category: Category) {
        progressBar.visibility = View.GONE
        if(category.name == "Popular"){
            this.bestMovies.addAll(category.movies!!)
            this.viewPagerAdapter.notifyDataSetChanged()
        }else {
            this.categories.add(category)
            this.bottomAdapter.notifyDataSetChanged()
        }
    }

    override fun onResult(movie: Movie) {
        TODO("Not yet implemented")
    }

    override fun onResult(genres: List<Genres>) {
        TODO("Not yet implemented")
    }

    override fun onResult(sere: Serie) {
        TODO("Not yet implemented")
    }

    override fun onResult(url: String) {
        TODO("Not yet implemented")
    }

    override fun onFailure(message: String) {
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
    }
}