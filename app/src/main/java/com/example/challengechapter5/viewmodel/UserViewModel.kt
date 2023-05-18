package com.example.challengechapter5.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.challengechapter5.room.User
import com.example.challengechapter5.room.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("unused")
@HiltViewModel
class UserViewModel @Inject constructor(application: Application, private var dao: UserDao) : AndroidViewModel(application) {
    @Suppress("JoinDeclarationAndAssignment")
    var userProfile : MutableLiveData<User>

    init {
        userProfile = MutableLiveData()
    }

    fun getDataUserProfile() : MutableLiveData<User>{
        return userProfile
    }
    fun getDataUser(email : String){
        viewModelScope.launch {
            val listUser = dao.getProfileUser(email)
            userProfile.postValue(listUser)
        }
    }

    fun insertDataUser(user: User){
        dao.insertProfileUser(user)
    }

    fun updateDataUser(user: User){
        dao.updateProfileUser(user)
    }

}