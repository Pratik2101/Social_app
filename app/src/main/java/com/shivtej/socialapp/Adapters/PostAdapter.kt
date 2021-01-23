package com.shivtej.socialapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.shivtej.socialapp.Models.Posts
import com.shivtej.socialapp.R
import com.shivtej.socialapp.Utils

class PostAdapter(options: FirestoreRecyclerOptions<Posts>, val listener: IPostAdapter) : FirestoreRecyclerAdapter<Posts, PostAdapter.PostViewHolder>(
    options
) {

    class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val postText: TextView = itemView.findViewById(R.id.postTitle)
        val userText: TextView = itemView.findViewById(R.id.userName)
        val createdAt: TextView = itemView.findViewById(R.id.createdAt)
        val likeCount: TextView = itemView.findViewById(R.id.likeCount)
        val userImage: ImageView = itemView.findViewById(R.id.userImg)
        val likeButton: ImageView = itemView.findViewById(R.id.likeBtn)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
          val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        val viewHolder = PostViewHolder(view)
        viewHolder.likeButton.setOnClickListener {
              listener.onLikeClicked(snapshots.getSnapshot(viewHolder.adapterPosition).id)
        }
         return viewHolder

    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: Posts) {
        holder.postText.text = model.text
        holder.userText.text = model.createdBy.name
        holder.likeCount.text = model.likedBy.size.toString()
        holder.createdAt.text = Utils.getTimeAgo(model.createdAt)

        val currentUser = FirebaseAuth.getInstance().currentUser!!.uid
        val isLiked = model.likedBy.contains(currentUser)
        if(isLiked){
            holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.context, R.drawable.ic_like))
        }
        else{
            holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.context, R.drawable.ic_unlike))
        }

    }
}

interface IPostAdapter{
    fun onLikeClicked(postId: String)
}