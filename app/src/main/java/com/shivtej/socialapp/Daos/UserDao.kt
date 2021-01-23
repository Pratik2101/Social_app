package com.shivtej.socialapp.Daos

import android.annotation.SuppressLint
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.shivtej.socialapp.Models.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class UserDao {

    private val db = FirebaseFirestore.getInstance()
    private val userCollection = db.collection("Users")

    @SuppressLint("RestrictedApi")
    fun addUser(user: Users){
        user?.let {
            GlobalScope.launch(Dispatchers.IO) {

                userCollection.document(user.uid!!).set(it)
            }
        }
    }

    fun getUserById(uid: String): Task<DocumentSnapshot>{
        return userCollection.document(uid).get()
    }

}