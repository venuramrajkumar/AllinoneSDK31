package com.example.allinone.ui.mulititableroom.entities.relations

import androidx.room.Entity

// for n to n relations
@Entity(primaryKeys = ["studentName","subjectName"])
data class StudentsSujbectCrossRef(
    //These two are primary keys of Studnet and Subject
    val studentName : String,
    val subjectName : String,


)
