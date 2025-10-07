package com.example.myapplication.ui.access

import androidx.annotation.DrawableRes
import com.example.myapplication.R

// A simple data class to represent a person with a name and a drawable resource for their image.
data class Person(
    val name: String,
    @DrawableRes val imageResId: Int = R.drawable.placeholder_profile_1 // Default profile image
)
