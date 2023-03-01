package com.example.infinity.adaptes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.infinity.R
import com.example.infinity.fragments.FragmentMovieItem
import com.example.infinity.fragments.FragmentSerieItem
import com.example.infinity.model.Movie
import com.example.infinity.model.Serie
import com.example.infinity.request.Urls
import com.example.infinity.utils.Util
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso

class SerieAdapter(
    private val context: Fragment,
    private val series: List<Serie>,
    private val layout: Int
) :RecyclerView.Adapter<SerieAdapter.SerieViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout,parent,false)
        return SerieViewHolder(view)
    }

    override fun onBindViewHolder(holder: SerieViewHolder, position: Int) {
        val serie = series[position]
        holder.bind(serie)
    }

    override fun getItemCount(): Int {
        return series.size
    }

    inner class SerieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(serie: Serie){
            val imgCover: RoundedImageView = itemView.findViewById(R.id.img_item)
            val title: TextView? = itemView.findViewById(R.id.title_movie)
            imgCover.setOnClickListener {
                //onItemClickListener?.invoke(FragmentMovieItem(movie))
                Util.replaceFragment(context, FragmentSerieItem(serie))
            }
            title.let {
                it?.text = serie.title
            }
            Picasso.get().load(Urls.getUrlsImg(serie.imgUrl)).into(imgCover)
        }
    }
}