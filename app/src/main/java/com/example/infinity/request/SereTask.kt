package com.example.infinity.request

import android.os.Looper
import android.util.Log
import com.example.infinity.model.Serie
import com.example.infinity.utils.Util
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.Executors

class SereTask(private val callBack: CallBack){
    private val executor = Executors.newSingleThreadExecutor()
    private val handler = android.os.Handler(Looper.getMainLooper())

    fun execute(url:String){

        executor.execute{
            try {
                Log.e("urlSerie",url)
                val jsonString = Util.requests(url)
                handler.post{
                    callBack.onResult(toSere(jsonString))
                }
            }catch (e:IOException){
                val message = e.message?:"Erro desconhecido"
                handler.post{
                    callBack.onFailure(message)
                }
            }
        }
    }

    private fun toSere(jsonAsString:String): Serie{
        Log.i("Json",jsonAsString)
        val jsonRoot = JSONObject(jsonAsString)
        val id = jsonRoot.getInt("id")
        val title = jsonRoot.getString("name")
        val imgUrl = jsonRoot.getString("backdrop_path")
        val overView = jsonRoot.getString("overview")
        CategoryTask("tv",callBack).execute(Urls.getUrlsSimilar(id,"tv"))

        return Serie(
            id = id,
            title = title,
            imgUrl = imgUrl,
            overView = overView
        )
    }
}