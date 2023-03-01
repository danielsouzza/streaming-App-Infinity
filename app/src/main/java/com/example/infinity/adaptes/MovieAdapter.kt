package com.example.infinity.adaptes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

import androidx.recyclerview.widget.RecyclerView
import com.example.infinity.R
import com.example.infinity.fragments.FragmentMovieItem
import com.example.infinity.model.Movie
import com.example.infinity.request.Urls
import com.example.infinity.utils.Util
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso

class MovieAdapter(private val context: Fragment,
                   private val movies: List<Movie>,
                   private val layout: Int):RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout,parent,false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(movie: Movie){
            val imgCover: RoundedImageView = itemView.findViewById(R.id.img_item)
            val title: TextView? = itemView.findViewById(R.id.title_movie)
            imgCover.setOnClickListener {
                //onItemClickListener?.invoke(FragmentMovieItem(movie))
                Util.replaceFragment(context,FragmentMovieItem(movie))
            }
            title.let {
                it?.text = movie.title
            }
            Picasso.get().load(Urls.getUrlsImg(movie.imgUrl)).into(imgCover)
        }
    }
}