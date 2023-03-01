package com.example.infinity.adaptes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.infinity.R
import com.example.infinity.model.Category

class CategoryAdapter (
    private val context: Fragment,
    private val categories: List<Category>) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item,parent,false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int {
        return categories.size;
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(category: Category){
            val txtTitle:TextView = itemView.findViewById(R.id.txt_title)
            val rvCategory: RecyclerView = itemView.findViewById(R.id.rv_category)
            txtTitle.text = category.name
            rvCategory.layoutManager = LinearLayoutManager(itemView.context,RecyclerView.HORIZONTAL,false)
            if(category.movies != null){
                rvCategory.adapter =  MovieAdapter(context,category.movies,
                    R.layout.move_item
                )
            }else if(category.series != null){
                rvCategory.adapter =  SerieAdapter(context,category.series,
                    R.layout.move_item
                )
            }

        }
    }
}