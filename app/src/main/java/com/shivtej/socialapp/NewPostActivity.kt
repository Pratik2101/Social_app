package com.shivtej.socialapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.android.material.button.MaterialButton
import com.shivtej.socialapp.Daos.PostDao

class NewPostActivity : AppCompatActivity() {

    private lateinit var mPost: EditText
    private lateinit var mPostBtn: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)

        mPost = findViewById(R.id.et_post)

    }

    fun createPost(view: View) {
        val input = mPost.text.toString().trim()
        if(input.isNotEmpty()){
            val postDao = PostDao()
            postDao.addPost(input)
            finish()

        }
    }
}