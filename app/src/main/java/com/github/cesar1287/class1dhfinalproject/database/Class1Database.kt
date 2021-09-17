package com.github.cesar1287.class1dhfinalproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.cesar1287.class1dhfinalproject.modeldb.Movie

object Class1Database {

    @Database(entities = [Movie::class], version = 1)
    abstract class Class1RoomDatabase : RoomDatabase() {
        abstract fun movieDao(): MovieDao
    }

    fun getDatabase(context: Context) : Class1RoomDatabase {
        return Room.databaseBuilder(
            context,
            Class1RoomDatabase::class.java, "class1_db"
        ).build()
    }
}