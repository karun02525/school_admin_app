package org.dps.admin.ui.create

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_assign_teacher.*
import org.dps.admin.R
import org.dps.admin.model.ClassData
import org.dps.admin.model.TeacherData
import org.dps.admin.mvvm.ClassViewModel
import org.dps.admin.utils.toast
import org.koin.android.viewmodel.ext.android.viewModel

class AssignTeacherActivity : AppCompatActivity() {
    private val viewModel: ClassViewModel by viewModel()
    private var class_id = ""
    private var teacher_id = ""
    private var sectionName = ""
    private var className = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assign_teacher)
        hideShowProgress(true)
        setupViewModel()



        btnSubmit.setOnClickListener {
            hideShowProgress(true)
            viewModel.assignTeacherAsync(class_id,className,sectionName,teacher_id)
        }
    }

    private fun setupViewModel() {
        viewModel.classData.observe(this, Observer {
            hideShowProgress(false)
            setSpClass(it)
        })
        viewModel.teacherList.observe(this, Observer {
            hideShowProgress(false)
            parseTeacher(it)
        })
        viewModel.msg.observe(this, Observer {
            hideShowProgress(false)
           toast(it)
        })
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
            }

    }
    private fun parseTeacher(list: List<TeacherData>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_teacher.setAdapter(adapter)

        sp_teacher.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val data: TeacherData = parent.adapter.getItem(position) as TeacherData
                teacher_id = data.id.toString()
            }

    }

    private fun setSpSection(section: List<String>) {
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, section)
        sp_section.setAdapter(adapter)
        sp_section.onItemClickListener =
            AdapterView.OnItemClickListener { parent, arg1, position, id ->
                sectionName = section[position]
            }
    }

    private fun hideShowProgress(flag: Boolean) {
        if (flag) progress_circular.visibility = View.VISIBLE else progress_circular.visibility =
            View.GONE
    }
}