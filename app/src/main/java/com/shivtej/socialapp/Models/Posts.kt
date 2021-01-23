package com.shivtej.socialapp.Models

import com.google.firebase.firestore.auth.User

data class Posts(
    val text: String = "",
    val createdBy: Users = Users(),
    val createdAt: Long = 0L,
    val likedBy: ArrayList<String> = ArrayList()
       )
