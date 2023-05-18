@file:Suppress("PrivatePropertyName", "PrivatePropertyName")

package com.example.challengechapter5.dsprefs

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreUser(val context: Context) {

    private val Context.dataStore by preferencesDataStore("account_user")

    private val STATUS_USER = stringPreferencesKey("status_account")
    private val EMAIL_USER = stringPreferencesKey("email_user")

    @Suppress("unused")
    suspend fun saveDataUser(status : String, email : String){
        context.dataStore.edit {
            it[STATUS_USER] = status
            it[EMAIL_USER] = email
        }
    }

    suspend fun deleteDataUser(){
        context.dataStore.edit {
            try {
                it.clear()
            }catch (e: Exception){
                Log.d("ERROR_DELETE", e.message.toString())
            }
        }
    }

    val statusUser : Flow<String> = context.dataStore.data.map {
        it[STATUS_USER]?: ""
    }

    val emailUser : Flow<String> = context.dataStore.data.map {
        it[EMAIL_USER]?: ""
    }

    @Suppress("unused", "unused", "unused")
    companion object{
        @Volatile
        private var instance: DataStoreUser? = null

        fun getInstance(context: Context): DataStoreUser {
            return instance ?: synchronized(this) {
                instance ?: DataStoreUser(context).also { instance = it }
            }
        }
    }
}