package org.dps.admin.utils

import android.content.Context
import org.dps.admin.ui.assign.AssignClassTeacherActivity
import org.dps.admin.ui.assign.AssignRollNoActivity
import org.dps.admin.ui.create.*
import org.dps.admin.ui.delete.DeleteClassActivity
import org.dps.admin.ui.show.ShowClassWiseStudentsActivity

object MenuUtils {

    val data: HashMap<String, List<String>>
        get() {
            val listData = LinkedHashMap<String, List<String>>()
            val classs = ArrayList<String>()
            classs.add("Create a class")
            classs.add("Delete a class")
            classs.add("Show class wise students")

            val parent = ArrayList<String>()
            parent.add("Create a parent")
            parent.add("Update a parent")
            parent.add("Delete a parent")
            parent.add("Search a parent")
            parent.add("Search all parent")

            val student = ArrayList<String>()
            student.add("Create a student")
            student.add("Update a student")
            student.add("Delete a student")
            student.add("Search a student")
            student.add("Search all student")

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
            listData["Parent"] = parent
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
            "Show class wise students" -> {
                startNewActivity(ShowClassWiseStudentsActivity::class.java)
            }
            "Search all class" -> {
                startNewActivity(CreateClassActivity::class.java)
            }
            "Create a parent" -> {
                startNewActivity(CreateParentActivity::class.java)
            }
            "Update a parent" -> {
                startNewActivity(CreateClassActivity::class.java)
            }
            "Delete a parent" -> {
                startNewActivity(CreateClassActivity::class.java)
            }
            "Search a parent" -> {
                startNewActivity(CreateClassActivity::class.java)
            }
            "Search all parent" -> {
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
                startNewActivity(CreateClassActivity::class.java)
            }
            "Search all student" -> {
                startNewActivity(CreateClassActivity::class.java)
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
                startNewActivity(CreateClassActivity::class.java)
            }
            "Search all teacher" -> {
                startNewActivity(CreateClassActivity::class.java)
            }"Assign Roll No" -> {
                startNewActivity(AssignRollNoActivity::class.java)
            }"Assign Class Teacher" -> {
                startNewActivity(AssignClassTeacherActivity::class.java)
            }

        }
    }
}