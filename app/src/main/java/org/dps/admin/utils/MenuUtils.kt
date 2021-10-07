package org.dps.admin.utils

import android.content.Context
import org.dps.admin.ui.create.CreateClassActivity
import org.dps.admin.ui.create.CreateParentActivity
import org.dps.admin.ui.create.CreateStudentActivity
import org.dps.admin.ui.create.CreateTeacherActivity

object MenuUtils {

    val data: HashMap<String, List<String>>
        get() {
            val listData = LinkedHashMap<String, List<String>>()
            val classs = ArrayList<String>()
            classs.add("Create a class")
            classs.add("Update a class")
            classs.add("Delete a class")
            classs.add("Search a class")
            classs.add("Search all classes")

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


            listData["Class"] = classs
            listData["Parent"] = parent
            listData["Student"] = student
            listData["Teacher"] = teacher

            return listData
        }

    fun Context.navigate(name:String){
        when (name) {
            "Create a class" -> {
                startNewActivity(CreateClassActivity::class.java)
            }
            "Update a class" -> {
                startNewActivity(CreateClassActivity::class.java)
            }
            "Delete a class" -> {
                startNewActivity(CreateClassActivity::class.java)
            }
            "Search a class" -> {
                startNewActivity(CreateClassActivity::class.java)
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
            }

        }
    }
}