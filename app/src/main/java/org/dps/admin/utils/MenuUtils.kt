package org.dps.admin.utils

import android.content.Context
import org.dps.admin.ui.assign.AssignClassTeacherActivity
import org.dps.admin.ui.assign.AssignRollNoActivity
import org.dps.admin.ui.create.*
import org.dps.admin.ui.delete.DeleteClassActivity
import org.dps.admin.ui.search.*

object MenuUtils {

    val data: HashMap<String, List<String>>
        get() {
            val listData = LinkedHashMap<String, List<String>>()
            val classs = ArrayList<String>()
            classs.add("Create a class")
            classs.add("Delete a class")

            val student = ArrayList<String>()
            student.add("Create a student")
            student.add("Update a student")
            student.add("Delete a student")
            student.add("Search a student")
            student.add("Search all student")
            student.add("Search class wise students")

            val teacher = ArrayList<String>()
            teacher.add("Create a teacher")
            teacher.add("Update a teacher")
            teacher.add("Delete a teacher")
            teacher.add("Search a teacher")
            teacher.add("Search all teacher")

            val assignRollNo = ArrayList<String>()
            assignRollNo.add("Assign Roll No")
            assignRollNo.add("Pending assign roll no")


            val assignClassTeacher = ArrayList<String>()
            assignClassTeacher.add("Assign Class Teacher")


            listData["Class"] = classs
            listData["Student"] = student
            listData["Teacher"] = teacher
            listData["Assign Roll No"] = assignRollNo
            listData["Assign Class Teacher"] = assignClassTeacher

            return listData
        }

    fun Context.navigate(name:String){
        when (name) {
            "Create a class" -> {
                startNewActivity(CreateClassActivity::class.java)
            }
            "Delete a class" -> {
                startNewActivity(DeleteClassActivity::class.java)
            }
            "Search all class" -> {
                startNewActivity(CreateClassActivity::class.java)
            }
            "Create a student" -> {
                startNewActivity(CreateStudentActivity::class.java)
            }
            "Update a student" -> {
                startNewActivity(CreateClassActivity::class.java)
            }
            "Delete a student" -> {
                startNewActivity(CreateClassActivity::class.java)
            }
            "Search a student" -> {
                startNewActivity(SearchStudentByIdActivity::class.java)
            }
            "Search all student" -> {
                startNewActivity(SearchStudentActivity::class.java)
            }
            "Search class wise students" -> {
                startNewActivity(SearchClassWiseStudentsActivity::class.java)
            }
            "Create a teacher" -> {
                startNewActivity(CreateTeacherActivity::class.java)
            }
            "Update a teacher" -> {
                startNewActivity(CreateClassActivity::class.java)
            }
            "Delete a teacher" -> {
                startNewActivity(CreateClassActivity::class.java)
            }
            "Search a teacher" -> {
                startNewActivity(SearchTeacherByIdActivity::class.java)
            }
            "Search all teacher" -> {
                startNewActivity(ShowTeacherActivity::class.java)
            }"Assign Roll No" -> {
                startNewActivity(AssignRollNoActivity::class.java)
            }"Assign Class Teacher" -> {
                startNewActivity(AssignClassTeacherActivity::class.java)
            }

        }
    }
}