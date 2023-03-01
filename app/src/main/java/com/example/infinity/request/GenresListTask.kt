package com.example.infinity.request

import android.os.Looper
import com.example.infinity.model.Genres
import com.example.infinity.utils.Util
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.Executors

class GenresListTask(private val callback: CallBack) {
    private val executor = Executors.newSingleThreadExecutor()
    private val handler = android.os.Handler(Looper.getMainLooper())

    fun execute(url: String) {
        executor.execute {
            try {
                val jsonAsString = Util.requests(url)
                handler.post {
                    callback.onResult(toGenres(jsonAsString))
                }
            } catch (e: IOException) {
                val message = e.message ?: "Erro desconhecido"
                handler.post {
                    callback.onFailure(message)
                }
            }
        }
    }

    private fun toGenres(jsonAsString:String): List<Genres>{
        val genres = mutableListOf<Genres>()
        val jsonRoot = JSONObject(jsonAsString)
        val jsonGenres = jsonRoot.getJSONArray("genres")
        for(i in 0 until jsonGenres.length()){
            val genre = Genres(
                jsonGenres.getJSONObject(i).getInt("id"),
                jsonGenres.getJSONObject(i).getString("name")
            )
            genres.add(genre)
        }

        return genres
    }
}