package com.github.cesar1287.class1dhfinalproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.github.cesar1287.class1dhfinalproject.modeldb.GenreDb
import com.github.cesar1287.class1dhfinalproject.modeldb.MovieDb
import com.github.cesar1287.class1dhfinalproject.modeldb.MovieGenreCrossRef

object Class1Database {

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE genre ADD COLUMN xpto TEXT")
        }
    }

    @Database(entities = [MovieDb::class,
        GenreDb::class, MovieGenreCrossRef::class], version = 2)
    abstract class Class1RoomDatabase : RoomDatabase() {
        abstract fun movieDao(): MovieDao
        abstract fun genreDao(): GenreDao
        abstract fun movieGenreDao(): MovieGenreCrossRefDao
    }

    fun getDatabase(context: Context) : Class1RoomDatabase {
        return Room.databaseBuilder(
            context,
            Class1RoomDatabase::class.java, "class1_db"
        ).addMigrations(
            MIGRATION_1_2
        ).build()
    }
}