package com.example.challengechapter5.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challengechapter5.databinding.ItemFilmBinding
import com.example.challengechapter5.model.ResultFilm

@Suppress("unused")
class FilmAdapter(private var listFilm : List<ResultFilm>) : RecyclerView.Adapter<FilmAdapter.ViewHolder>() {

    var onClickItem : ((ResultFilm)->Unit)? = null

    class ViewHolder(var binding : ItemFilmBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindFilm(itemFilm : ResultFilm){
            binding.film = itemFilm
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindFilm(listFilm[position])
        Glide.with(holder.itemView)
            .load("https://image.tmdb.org/t/p/w500${listFilm[position].posterPath}")
            .into(holder.binding.imgFilm)

        holder.binding.progressRate.progress = (listFilm[position].voteAverage*10).toInt()
        holder.binding.itemFilm.setOnClickListener {
           onClickItem?.invoke(listFilm[position])
        }
    }

    override fun getItemCount(): Int {
        return listFilm.size
    }

    //supress
    @SuppressLint("NotifyDataSetChanged")
    fun setDataFilm(list : List<ResultFilm>){
        listFilm = list
        notifyDataSetChanged()
    }

}