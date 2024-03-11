package com.example.vkandroidtest.utlis

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.vkandroidtest.R

object ViewExtensions {

    fun ImageView.loadFromUrl(url: String) {
        Glide.with(this)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(this)
    }
}