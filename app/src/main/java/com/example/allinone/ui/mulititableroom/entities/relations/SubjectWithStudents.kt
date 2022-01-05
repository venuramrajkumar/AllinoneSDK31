package com.example.allinone.ui.mulititableroom.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.allinone.ui.mulititableroom.entities.Student
import com.example.allinone.ui.mulititableroom.entities.Subject

data class SubjectWithStudents(

    @Embedded
    val subject : Subject,

    @Relation(
        parentColumn = "subjectName",
        entityColumn = "studentName",
        associateBy = Junction(StudentsSujbectCrossRef::class)
    )

    val students : List<Student>

)
