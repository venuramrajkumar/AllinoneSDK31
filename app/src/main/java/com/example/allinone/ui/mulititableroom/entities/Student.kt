package com.example.allinone.ui.mulititableroom.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Student(
    @PrimaryKey(autoGenerate = false)
    val studentName : String,

    val semister : String,

    val schoolName : String
    )
