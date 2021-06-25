package com.example.madarsofttask.repository

import androidx.lifecycle.LiveData
import com.example.madarsofttask.database.UserDao
import com.example.madarsofttask.database.entity.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val dao: UserDao) {

    suspend fun insert(user:User){
         dao.insertAll(user)
    }

    suspend fun getAllUsers():List<User>{
        return dao.getAll()
    }

}