package com.example.challengechapter5.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE email = :emailuser ")
    fun getProfileUser(emailuser : String) : User

    @Update
    fun updateProfileUser(user: User)

    @Insert
    fun insertProfileUser(user: User)
}