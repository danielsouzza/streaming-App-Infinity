package com.example.infinity.adaptes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.infinity.R
import com.example.infinity.fragments.FragmentMovieItem
import com.example.infinity.model.Movie
import com.example.infinity.utils.Util
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso

class ViewPageAdapter(private val context: Fragment,
                      private val movies: MutableList<Movie>,private val viewPager2: ViewPager2): RecyclerView.Adapter<ViewPageAdapter.ViewPagerHolder>(){

    private val runnable = Runnable {
        movies.addAll(movies)
        notifyDataSetChanged()
    }

    inner class ViewPagerHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind(movie: Movie){
            val imgCover: RoundedImageView = itemView.findViewById(R.id.img_item)
            val title: TextView? = itemView.findViewById(R.id.title_movie)
            imgCover.setOnClickListener {
                // onItemClickListener?.invoke(FragmentMovieItem(movie))
                Util.replaceFragment(context,FragmentMovieItem(movie))
            }
            title.let {
                it?.text = movie.title
            }
            val imgUrl = "https://image.tmdb.org/t/p/w500" + movie.imgUrl
            Picasso.get().load(imgUrl).into(imgCover)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bast_movie_item,parent,false)
        return ViewPagerHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        if(position == movies.size-1){
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int = movies.size
}