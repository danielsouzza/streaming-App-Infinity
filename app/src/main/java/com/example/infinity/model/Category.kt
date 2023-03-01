package com.example.infinity.model

data class Category(val name:String,val movies: List<Movie>? = null, val series:List<Serie>? = null)
