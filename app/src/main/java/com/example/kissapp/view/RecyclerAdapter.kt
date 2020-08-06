package com.example.kissapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.kissapp.R
import com.example.kissapp.model.CharacterModel

class RecyclerAdapter(private var dataSet: List<CharacterModel>,  private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var characterName: TextView = itemView.findViewById(R.id.textView)
        var characterImage: ImageView = itemView.findViewById(R.id.item_image)

        fun bind(position: Int, clickListener: OnItemClickListener) {
            itemView.setOnClickListener {
                clickListener.onItemClicked(position)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.character_card_view, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.characterName.text=dataSet[position].name

        Glide.with(holder.itemView)
            .load(dataSet[position].pictureUrl)
            .centerCrop()
            .override(100,100)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(50)))
            .placeholder(R.drawable.broken_image)
            .error(R.drawable.broken_image)
            .fallback(R.drawable.broken_image)
            .into(holder.characterImage)

        holder.bind(position,itemClickListener)
    }


    interface OnItemClickListener {
        fun onItemClicked(position: Int)
    }

}