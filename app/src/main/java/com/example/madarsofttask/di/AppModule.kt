package com.example.madarsofttask.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.madarsofttask.database.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {



    @Provides
    @Singleton
     fun provideDataBase(application : Application) : UserDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            UserDatabase::class.java,
            "USER-DB"
        )
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    fun provideTaskDao(db: UserDatabase) = db.userDao()
}