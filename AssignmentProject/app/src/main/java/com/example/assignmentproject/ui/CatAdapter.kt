package com.example.assignmentproject.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assignmentproject.Data.CatImage
import com.example.assignmentproject.R
import com.squareup.picasso.Picasso

class CatAdapter(private var catImages: List<CatImage>) :
    RecyclerView.Adapter<CatAdapter.ViewHolder>() {

    // Implement onCreateViewHolder, onBindViewHolder, getItemCount, etc.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val catImage = catImages[position]
        holder.bind(catImage)
    }

    override fun getItemCount(): Int {
        return catImages.size
    }

    fun updateData(newData: List<CatImage>) {
        catImages = catImages+ newData
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val catImageView: ImageView  = view.findViewById(R.id.catImageView)
        fun bind(catImage: CatImage) {
           /* Glide.with(itemView.context)
                .load(catImage.url)
                .into(catImageView)*/

        Picasso.get().load(catImage.url).into(catImageView)
        }
    }
}
