package com.example.allinone.ui.mulititableroom.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.allinone.ui.mulititableroom.entities.School
import com.example.allinone.ui.mulititableroom.entities.Student

//1 to N relation
data class SchoolWithStudents(
    @Embedded
    val school : School,

    @Relation(
        parentColumn = "schoolName",
        entityColumn = "schoolName"
    )

    val students : List<Student>


)
