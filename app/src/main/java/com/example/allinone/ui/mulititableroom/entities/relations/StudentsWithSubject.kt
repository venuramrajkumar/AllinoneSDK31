package com.example.allinone.ui.mulititableroom.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.allinone.ui.mulititableroom.entities.Student
import com.example.allinone.ui.mulititableroom.entities.Subject

data class StudentsWithSubject(

    @Embedded
    val student : Student,

    @Relation (
        parentColumn = "studentName",
        entityColumn = "subjectName",
        associateBy = Junction(StudentsSujbectCrossRef::class)
            )

    val subjects: List<Subject>


)
