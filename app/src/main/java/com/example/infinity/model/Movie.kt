package com.example.infinity.model

data class Movie(
    val id:Int=0,
    val imgUrl:String,
    val title:String ="",
    val overView:String="",
    var urlVideo:String="",
    val suggestion: List<Movie>? = null
)
