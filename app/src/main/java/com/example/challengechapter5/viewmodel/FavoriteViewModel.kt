package com.example.challengechapter5.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.challengechapter5.room.FilmFavorite
import com.example.challengechapter5.room.FilmFavoriteDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("all")
@HiltViewModel
class FavoriteViewModel @Inject constructor(application: Application, private var dao: FilmFavoriteDao) : AndroidViewModel(application) {

    @Suppress("JoinDeclarationAndAssignment")
    lateinit var listFilmFavorite : MutableLiveData<List<FilmFavorite>>
    lateinit var dataFilmFavorite : MutableLiveData<FilmFavorite?>

    init {
        listFilmFavorite = MutableLiveData()
        dataFilmFavorite = MutableLiveData()
    }

    fun getDataListFilmFavorit() : MutableLiveData<List<FilmFavorite>> {
        return listFilmFavorite
    }

    fun getDataDetailFilmFavorit() : MutableLiveData<FilmFavorite?> {
        return dataFilmFavorite
    }

    fun getDataFilmFavorite(email : String){
        viewModelScope.launch {
            val listUser = dao.getFilmFavorite(email)
            listFilmFavorite.postValue(listUser)
        }
    }

    fun checkListFavorite(id : Int, email : String, filmFavorite: FilmFavorite){
        viewModelScope.launch {
            val listFilm = dao.getDetailFilmFavorite(id, email)

            if(listFilm != null){
                deleteDataFilmFavorite(listFilm)
                dataFilmFavorite.postValue(null)
            }else{
                insertDataFilmFavorite(filmFavorite)
                dataFilmFavorite.postValue(listFilm)
            }
        }
    }

    fun getDetailFilmFavorite(id: Int, email: String){
        viewModelScope.launch {
            val listFilm = dao.getDetailFilmFavorite(id, email)
            dataFilmFavorite.postValue(listFilm)
        }

    }



    @Suppress("all")
    fun insertDataFilmFavorite(filmFavorite: FilmFavorite){
        dao.insertFilmFavorite(filmFavorite)
    }

    fun deleteDataFilmFavorite(filmFavorite: FilmFavorite){
        dao.deleteFilmFavorite(filmFavorite)
    }

}