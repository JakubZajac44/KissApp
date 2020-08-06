package com.example.kissapp.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.kissapp.R
import com.google.gson.annotations.SerializedName


data class CharacterModel(

    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("image")
    var pictureUrl: String,
    @SerializedName("gender")
    var gender: String,
    @SerializedName("status")
    var isAlive: String,
    @SerializedName("origin")
    var originLocation: Origin,
    @SerializedName("location")
    var currentLocation: Location



)

@BindingAdapter("bind:imageUrl")
fun loadImage(view: ImageView, url: String?) {
    if(!url.isNullOrEmpty())
        Glide.with(view.context)
            .load(url)
            .centerCrop()
            .override(200,200)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(100)))
            .placeholder(R.drawable.broken_image)
            .error(R.drawable.broken_image)
            .fallback(R.drawable.broken_image)
            .into(view)

}

data class CharacterList(
    @SerializedName("results")
    var characterModels: MutableList<CharacterModel> = mutableListOf()
)

data class Origin(
    @SerializedName("name")
    var location: String
)

data class Location(
    @SerializedName("name")
    var location: String
)



