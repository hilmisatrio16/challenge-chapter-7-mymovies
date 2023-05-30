package com.example.challengechapter5.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.challengechapter5.databinding.ItemCommentBinding
import com.example.challengechapter5.model.DataComment

@Suppress("all", "unused")
class CommentsAdapter(private var listFilm : List<DataComment>) : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    var onClickItem : ((DataComment)->Unit)? = null

    class ViewHolder(var binding : ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindFilm(itemFilm : DataComment){
            binding.comment = itemFilm
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindFilm(listFilm[position])

    }

    override fun getItemCount(): Int {
        return listFilm.size
    }

    //supress
    @SuppressLint("NotifyDataSetChanged")
    fun setDataFilm(list : List<DataComment>){
        listFilm = list
        notifyDataSetChanged()
    }

}