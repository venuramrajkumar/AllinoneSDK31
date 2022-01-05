package com.example.allinone.ui.mulititableroom.entities.relations

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.allinone.ui.mulititableroom.entities.Director
import com.example.allinone.ui.mulititableroom.entities.School
import com.example.allinone.ui.mulititableroom.entities.Student
import com.example.allinone.ui.mulititableroom.entities.Subject

@Database(
    entities = [School::class, Student::class, Director::class, Subject::class, StudentsSujbectCrossRef::class],
    version = 1
)
abstract class SchoolDatabase : RoomDatabase() {

    abstract val schoolDao: SchoolDao

    companion object {

        @Volatile
        private var instance: SchoolDatabase? = null

        fun getInstance(context: Context): SchoolDatabase {
            synchronized(this) {
                return instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    SchoolDatabase::class.java,
                    "school_db"
                ).build().apply {
                    instance = this
                }
            }

        }

    }
}