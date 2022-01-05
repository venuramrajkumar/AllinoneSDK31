package com.example.allinone.ui.mulititableroom.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.allinone.ui.mulititableroom.entities.Director
import com.example.allinone.ui.mulititableroom.entities.School

//1 to 1 relation
data class SchoolAndDirector(
    @Embedded
    val school: School,

    @Relation(
        parentColumn = "schoolName",
        entityColumn = "schoolName"
    )
    val director : Director
)