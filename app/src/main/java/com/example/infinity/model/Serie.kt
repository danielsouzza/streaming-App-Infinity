package com.example.infinity.model

data class Serie(
    val id:Int=0,
    val title: String = "",
    val imgUrl:String="",
    val overView:String="",
    var urlVideo:String = "",
    val suggestions: List<Serie>? = null

)
