package com.example.challengechapter5.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Suppress("unused")
@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) var id : Int = 0,
    var email : String,
    var username : String?,
    var namaLengkap : String?,
    var tanggalLahir : String?,
    var alamat : String?
)
