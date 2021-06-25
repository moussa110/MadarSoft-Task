package com.example.madarsofttask.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Int=0,
    val userName: String,
    val jobTitle: String,
    val gander: String,
    val age: Int
    ):Serializable
