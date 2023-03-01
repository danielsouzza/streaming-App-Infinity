package com.example.infinity.request

import com.example.infinity.model.Category
import com.example.infinity.model.Genres
import com.example.infinity.model.Movie
import com.example.infinity.model.Serie

interface CallBack {
    fun onPreExecute()
    fun onResult(category: Category)
    fun onResult(movie: Movie)
    fun onResult(genres: List<Genres>)
    fun onResult(sere:Serie)
    fun onResult(url: String)
    fun onFailure(message:String)

}