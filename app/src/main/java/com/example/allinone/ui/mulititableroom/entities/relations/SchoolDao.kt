package com.example.allinone.ui.mulititableroom.entities.relations

import androidx.room.*
import com.example.allinone.ui.mulititableroom.entities.Director
import com.example.allinone.ui.mulititableroom.entities.School
import com.example.allinone.ui.mulititableroom.entities.Student
import com.example.allinone.ui.mulititableroom.entities.Subject

@Dao
interface SchoolDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchool(school: School)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDirector(director: Director)

//1 to 1
    @Transaction //To avoid multi threading issues as we are dealing with multiple tables
    @Query("SELECT * FROM School WHERE schoolName = :schoolName")
    suspend fun getSchoolAndDirectorWithSchoolName(schoolName : String) : List<SchoolAndDirector>


    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student)

//    1 to n : it is similar to 1 to 1
    @Transaction //To avoid multi threading issues as we are dealing with multiple tables
    @Query("SELECT * FROM School WHERE schoolName = :schoolName")
    suspend fun getSchoolWithStudents(schoolName : String) : List<SchoolWithStudents>


    // n to n


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubject(subject: Subject)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudentSubjectCrossRef(studentsSujbectCrossRef: StudentsSujbectCrossRef)


    @Transaction
    @Query("select * from Subject where subjectName  = :subjectName ")
    suspend fun getStudentsOfSubject(subjectName : String) : List<SubjectWithStudents>

    @Transaction
    @Query("select * from Student where studentName  = :studentName ")
    suspend fun getSubjectsOfStudents(studentName : String) : List<StudentsWithSubject>





}