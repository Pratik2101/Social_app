package com.shivtej.socialapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.shivtej.socialapp.Adapters.IPostAdapter
import com.shivtej.socialapp.Adapters.PostAdapter
import com.shivtej.socialapp.Daos.PostDao
import com.shivtej.socialapp.Models.Posts

class MainActivity : AppCompatActivity(), IPostAdapter {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PostAdapter
    private lateinit var postDao: PostDao
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpRecyclerView()
        mAuth = FirebaseAuth.getInstance()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.logout_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.logout->{
                logOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logOut() {
        Firebase.auth.signOut()
        val intent = Intent(this, RegisterLoginActivity::class.java)
        startActivity(intent)
    }

    private fun setUpRecyclerView() {

        postDao = PostDao()
        val postCollection = postDao.postCollection
        val query = postCollection.orderBy("createdAt", Query.Direction.DESCENDING)
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Posts>().setQuery(query, Posts::class.java).build()

        recyclerView = findViewById(R.id.recyclerView)
        adapter = PostAdapter(recyclerViewOptions, this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    fun newPost(view: View) {
        val intent = Intent(this, NewPostActivity::class.java)
        startActivity(intent)
    }

    override fun onLikeClicked(postId: String) {
     postDao.updateLikes(postId)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}