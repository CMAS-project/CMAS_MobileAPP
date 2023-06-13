package com.capstone.bangkit.cmas.data.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
//    val id: String,
    val title: String,
    val description: String,
) : Parcelable
