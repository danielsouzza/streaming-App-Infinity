package com.example.infinity.request

import android.os.Looper
import com.example.infinity.model.Movie
import com.example.infinity.utils.Util
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.Executors

class MovieTask(private val callback: CallBack) {
    private val executor = Executors.newSingleThreadExecutor()
    private val handler = android.os.Handler(Looper.getMainLooper())

    fun execute(url:String){
        callback.onPreExecute()
        executor.execute{
            try {
                val jsonString = Util.requests(url)
                handler.post{
                    callback.onResult(toMovie(jsonString))
                }
            }catch (e:IOException){
                val message = e.message?:"Erro desconhecido"
                handler.post{
                    callback.onFailure(message)
                }
            }
        }
    }

    private fun toMovie(jsonAsString:String):Movie{
        val movie:Movie
        val jsonRoot = JSONObject(jsonAsString)
        val id = jsonRoot.getInt("id")
        val title = jsonRoot.getString("title")
        val imgUrl = jsonRoot.getString("backdrop_path")
        val overview = jsonRoot.getString("overview")
        CategoryTask("movie",callback).execute(Urls.getUrlsSimilar(id,"movie"))
        movie = Movie(
            id=id,
            title = title,
            imgUrl = imgUrl,
            overView = overview
        )
        return movie
    }
}