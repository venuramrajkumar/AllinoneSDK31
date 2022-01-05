package com.example.allinone.ui.mulititableroom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.allinone.R
import com.example.allinone.ui.mulititableroom.entities.Director
import com.example.allinone.ui.mulititableroom.entities.School
import com.example.allinone.ui.mulititableroom.entities.Student
import com.example.allinone.ui.mulititableroom.entities.Subject
import com.example.allinone.ui.mulititableroom.entities.relations.SchoolDao
import com.example.allinone.ui.mulititableroom.entities.relations.SchoolDatabase
import com.example.allinone.ui.mulititableroom.entities.relations.StudentsSujbectCrossRef
import kotlinx.coroutines.launch

class RoomDbDemoActivity : AppCompatActivity() {

    lateinit var dao: SchoolDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_db_demo)

        dao = SchoolDatabase.getInstance(this).schoolDao

        insertDataIntoDb()


    }

    private fun insertDataIntoDb() {
        val directors = listOf(
            Director("Mike Litoris", "Jake Wharton School"),
            Director("Jack Goff", "Kotlin School"),
            Director("Chris P. Chicken", "JetBrains School")
        )
        val schools = listOf(
            School("Jake Wharton School"),
            School("Kotlin School"),
            School("JetBrains School")
        )
        val subjects = listOf(
            Subject("Dating for programmers"),
            Subject("Avoiding depression"),
            Subject("Bug Fix Meditation"),
            Subject("Logcat for Newbies"),
            Subject("How to use Google")
        )
        val students = listOf(
            Student("Beff Jezos", "2", "Kotlin School"),
            Student("Mark Suckerberg", "5", "Jake Wharton School"),
            Student("Gill Bates", "8", "Kotlin School"),
            Student("Donny Jepp", "1", "Kotlin School"),
            Student("Hom Tanks", "2", "JetBrains School")
        )
        val studentSubjectRelations = listOf(
            StudentsSujbectCrossRef("Beff Jezos", "Dating for programmers"),
            StudentsSujbectCrossRef("Beff Jezos", "Avoiding depression"),
            StudentsSujbectCrossRef("Beff Jezos", "Bug Fix Meditation"),
            StudentsSujbectCrossRef("Beff Jezos", "Logcat for Newbies"),
            StudentsSujbectCrossRef("Mark Suckerberg", "Dating for programmers"),
            StudentsSujbectCrossRef("Gill Bates", "How to use Google"),
            StudentsSujbectCrossRef("Donny Jepp", "Logcat for Newbies"),
            StudentsSujbectCrossRef("Hom Tanks", "Avoiding depression"),
            StudentsSujbectCrossRef("Hom Tanks", "Dating for programmers")
        )

        lifecycleScope.launch {
            directors.forEach { dao.insertDirector(it) }
            schools.forEach { dao.insertSchool(it) }
            subjects.forEach { dao.insertSubject(it) }
            students.forEach { dao.insertStudent(it) }
            studentSubjectRelations.forEach { dao.insertStudentSubjectCrossRef(it) }

            val schoolWithDirector = dao.getSchoolAndDirectorWithSchoolName("Kotlin School")

            val schoolWithStudents = dao.getSchoolWithStudents("Kotlin School")


        }

    }
}