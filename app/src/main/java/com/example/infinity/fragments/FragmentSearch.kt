package com.example.infinity.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.infinity.R
import com.example.infinity.utils.Util

class FragmentSearch : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemMovies: LinearLayout = view.findViewById(R.id.category_movies)
        val itemSeries: LinearLayout = view.findViewById(R.id.category_series)

        itemMovies.setOnClickListener {
            Util.replaceFragment(this, FragmentMovies())
        }

        itemSeries.setOnClickListener {
            Util.replaceFragment(this, FragmentSeries())
        }
    }
}

