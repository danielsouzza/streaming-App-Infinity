package com.example.infinity.request

class Urls{
    companion object{
        private const val key = "f1932220f5506b9b275d2bbe9dbb9241"
        private val categories:List<String> = listOf("popular","now_playing","upcoming","top_rated")

        fun getUrlCategory(i:Int): String = "https://api.themoviedb.org/3/movie/${categories[i]}?api_key=${key}"
        fun getUrlMovie(id:Int): String = "https://api.themoviedb.org/3/movie/${id}?api_key=${key}"
        fun getUrlSere(id: Int): String = "https://api.themoviedb.org/3/tv/${id}?api_key=${key}"
        fun getUrlsImg(url:String): String = "https://image.tmdb.org/t/p/w500${url}"
        fun getUrlsSimilar(movie_id:Int,type:String): String = "https://api.themoviedb.org/3/${type}/${movie_id}/similar?api_key=${key}"
        fun getUrlVideo(id:Int): String = "https://api.themoviedb.org/3/movie/${id}%7D/videos?api_key=${key}"
        fun getUrlGenres(type:String) :String = "https://api.themoviedb.org/3/genre/${type}/list?api_key=${key}"
        fun getUrlCategoryByGenres(type: String,id:Int):String = "https://api.themoviedb.org/3/discover/${type}?api_key=${key}&with_genres=${id}"
    }
}


