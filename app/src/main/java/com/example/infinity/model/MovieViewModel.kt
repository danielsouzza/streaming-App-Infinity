package com.example.infinity.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MovieViewModel: ViewModel(){
    private val selectedItem: MutableLiveData<Movie> = MutableLiveData()

    fun setSelectedItem(movie: Movie){
        selectedItem.value = movie

    }

    fun getSelectedItem(): MutableLiveData<Movie>{
        return selectedItem
    }
}