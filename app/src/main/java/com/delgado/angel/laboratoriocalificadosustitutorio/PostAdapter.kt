package com.delgado.angel.laboratoriocalificadosustitutorio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.delgado.angel.laboratoriocalificadosustitutorio.databinding.ItemPostBinding

class PostAdapter (
    var list: List<PostResponse>,
    private val onItemClick: (PostResponse) -> Unit,
    private val onItemLongClick: (PostResponse) -> Unit
): RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val binding = ItemPostBinding.bind(view)

        fun bind(post: PostResponse) {
            binding.tvPostId.text = post.id.toString()
            binding.tvUserId.text = post.userId.toString()
            binding.tvTitle.text = post.title
            binding.tvBody.text = post.body

            binding.root.setOnClickListener {
                onItemClick(post);
            }

            binding.root.setOnLongClickListener {
                onItemLongClick(post)
                true
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemPostBinding.inflate(LayoutInflater.from(parent.context),parent ,false)
        return ViewHolder(view.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemPost = list[position]
        holder.bind(itemPost)
    }

    override fun getItemCount(): Int = list.size

}