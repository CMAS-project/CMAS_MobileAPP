package com.capstone.bangkit.cmas.data.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val id: Int,
    val name: String,
    val desc: String,
) : Parcelable
