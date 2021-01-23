package com.shivtej.socialapp.Daos

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import com.shivtej.socialapp.Models.Posts
import com.shivtej.socialapp.Models.Users
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostDao {

    val db = FirebaseFirestore.getInstance()
    val postCollection = db.collection("Posts")
    val auth = Firebase.auth

    fun addPost(text: String){
        val currentUserId = auth.currentUser!!.uid
        GlobalScope.launch {
            val userDao = UserDao()
            val user = userDao.getUserById(currentUserId).await().toObject(Users::class.java)!!
            val currentTime = System.currentTimeMillis()
            val post = Posts(text, user, currentTime)
            postCollection.document().set(post)

        }


    }

    fun getPostById(postId: String): Task<DocumentSnapshot> {
        return postCollection.document(postId).get()
    }

    fun updateLikes(postId: String){
        GlobalScope.launch {
            val currentUser = auth.currentUser!!.uid
            val post = getPostById(postId).await().toObject(Posts::class.java)!!
            val isLiked = post.likedBy.contains(currentUser)
            if(isLiked){
                post.likedBy.remove(currentUser)
            }else{
                post.likedBy.add(currentUser)
            }
            postCollection.document(postId).set(post)
        }

    }
}

