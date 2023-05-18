package com.example.challengechapter5.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challengechapter5.databinding.ItemFilmFavoriteBinding
import com.example.challengechapter5.room.FilmFavorite

@Suppress("unused")
class FilmFavoriteAdapter(private var listFilmFavorite : List<FilmFavorite>) : RecyclerView.Adapter<FilmFavoriteAdapter.ViewHolder>() {

    var onClickRemoveFavorit : ((FilmFavorite)->Unit)? = null

    class ViewHolder(var binding : ItemFilmFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindFilmFavorite(itemFilmFavorite : FilmFavorite){
            binding.filmfavorite = itemFilmFavorite
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
       val view = ItemFilmFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindFilmFavorite(listFilmFavorite[position])
        Glide.with(holder.itemView)
            .load("https://image.tmdb.org/t/p/w500${listFilmFavorite[position].posterPath}")
            .into(holder.binding.imgFilm)

        holder.binding.progressRate.progress = (listFilmFavorite[position].voteAverage*10).toInt()

        holder.binding.tvRemoveFilmFavorite.setOnClickListener {
            onClickRemoveFavorit?.invoke(listFilmFavorite[position])
        }
    }

    override fun getItemCount(): Int {
        return listFilmFavorite.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListFilmFavorite(list : List<FilmFavorite>){
        listFilmFavorite = list
        notifyDataSetChanged()
    }

}