package org.dps.admin.ui.assign

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_assign_rollno.*
import kotlinx.android.synthetic.main.activity_assign_rollno.btnSubmit
import kotlinx.android.synthetic.main.activity_assign_rollno.edit_rollno
import kotlinx.android.synthetic.main.activity_assign_rollno.progress_circular
import kotlinx.android.synthetic.main.activity_assign_rollno.sp_classes
import kotlinx.android.synthetic.main.custom_toolbar.*
import org.dps.admin.R
import org.dps.admin.model.ClassData
import org.dps.admin.model.StudentData
import org.dps.admin.mvvm.AssignClassTeacherViewModel
import org.dps.admin.mvvm.ClassViewModel
import org.dps.admin.utils.hideSoftKeyboard
import org.dps.admin.utils.messToast
import org.dps.admin.utils.toast
import org.koin.android.viewmodel.ext.android.viewModel

class AssignRollNoActivity : AppCompatActivity() {
    private val viewModel: AssignClassTeacherViewModel by viewModel()
    private var class_id = ""
    private var student_id = ""
    private var className = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assign_rollno)
        hideShowProgress(true)
        setupViewModel()



        btn_back.setOnClickListener { onBackPressed() }

        tv_toolbar.text="Assign Roll No"
        
        btnSubmit.setOnClickListener {
            val rollno = edit_rollno.text.toString()
            when {
                class_id.isBlank() -> {
                    mess("Please select class")
                }
                student_id.isBlank() -> {
                    mess("Please select student")
                }
                rollno.isEmpty() -> {
                    mess("Please enter roll no")
                }
                else -> {
                    hideShowProgress(true)
                    viewModel.assignRollNo(class_id, student_id,rollno)
                }
            }
        }
    }

    private fun mess(s: String) {
        hideSoftKeyboard()
        messToast(s)
    }

    private fun setupViewModel() {
        viewModel.getClasses()
        viewModel.classDataList.observe(this, Observer {
            hideShowProgress(false)
            setSpClass(it)
        })
        viewModel.studentsList.observe(this, Observer {
            hideShowProgress(false)
            parseStudent(it)
        })
        viewModel.msg.observe(this, Observer {
            hideShowProgress(false)
           toast(it)
        })
    }

    private fun parseStudent(list: List<StudentData>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_student.setAdapter(adapter)

        sp_student.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val data: StudentData = parent.adapter.getItem(position) as StudentData
                student_id = data.id.toString()
            }

    }


     private fun setSpClass(list: List<ClassData>) {
         val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)
         adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
         sp_classes.setAdapter(adapter)

         sp_classes.onItemClickListener =
             AdapterView.OnItemClickListener { parent, _, position, _ ->
                 val data: ClassData = parent.adapter.getItem(position) as ClassData
                 class_id = data.id.toString()
                 className = data.name.toString()
                 viewModel.getAllStudentByClassId(class_id)
                // setSpSection(data.section)
             }

     }

   /* private fun setSpSection(section: List<String>) {
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, section)
        sp_section.setAdapter(adapter)
        sp_section.onItemClickListener =
            AdapterView.OnItemClickListener { parent, arg1, position, id ->
                sectionName = section[position]
            }
    }*/

    private fun hideShowProgress(flag: Boolean) {
        if (flag) progress_circular.visibility = View.VISIBLE else progress_circular.visibility =
            View.GONE
    }
}