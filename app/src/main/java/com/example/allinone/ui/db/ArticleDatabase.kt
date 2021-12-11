package com.example.allinone.ui.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.allinone.ui.model.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters (Converters::class)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

    companion object {
        @Volatile
        private var instance: ArticleDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabse(context).also { instance = it }
        }

        private fun createDatabse(context: Context) =
            Room.databaseBuilder(context.applicationContext, ArticleDatabase::class.java, "news.db")
                .build()

    }

}