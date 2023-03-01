package com.example.infinity.request

import android.os.Looper
import android.util.Log
import com.example.infinity.model.Category

import com.example.infinity.model.Movie
import com.example.infinity.model.Serie
import com.example.infinity.utils.Util
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.Executors

class CategoryTask(private val type: String, private val callback: CallBack) {
    private val executor = Executors.newSingleThreadExecutor()
    private val handler = android.os.Handler(Looper.getMainLooper())

    fun execute(url: String, nameCategory: String = "") {
        callback.onPreExecute()
        executor.execute {
            try {
                Log.e("urlCategorie",url)
                val jsonAsString = Util.requests(url)
                handler.post {
                    if(type == "tv"){
                        callback.onResult(toSerie(jsonAsString, nameCategory))
                    }else{
                        callback.onResult(toMovies(jsonAsString, nameCategory))
                    }

                }

            } catch (e: IOException) {
                val message = e.message ?: "Erro desconhecido"
                handler.post {
                    callback.onFailure(message)
                }
            }
        }
    }

    private fun toMovies(jsonAsString: String, nameCategory: String): Category {
        Log.e("erro",jsonAsString)
        val category: Category
        val movies = mutableListOf<Movie>()
        val jsonRoot = JSONObject(jsonAsString)
        val jsonCategories = jsonRoot.getJSONArray("results")
        for (i in 0 until jsonCategories.length()) {
            val jsonCategory = jsonCategories.getJSONObject(i)
            val id = jsonCategory.getInt("id")
            val title = jsonCategory.getString("title")

            val imgUrl: String = if (nameCategory == "Popular") {
                jsonCategory.getString("backdrop_path")
            } else {
                jsonCategory.getString("poster_path")
            }

            movies.add(
                Movie(id, imgUrl, title)
            )
        }
        category = Category(nameCategory, movies)
        return category
    }

    private fun toSerie(jsonAsString: String, nameCategory: String): Category {
        Log.e("erro",jsonAsString)
        val category: Category
        val series = mutableListOf<Serie>()
        val jsonRoot = JSONObject(jsonAsString)
        val jsonCategories = jsonRoot.getJSONArray("results")
        for (i in 0 until jsonCategories.length()) {
            val jsonCategory = jsonCategories.getJSONObject(i)
            val id = jsonCategory.getInt("id")
            val title = jsonCategory.getString("name")
            val imgUrl: String = if (nameCategory == "Popular") {
                jsonCategory.getString("backdrop_path")
            } else {
                jsonCategory.getString("poster_path")
            }

            series.add(
                Serie(id=id, title=title,imgUrl=imgUrl)
            )
        }
        category = Category(nameCategory, series=series)
        return category
    }
}