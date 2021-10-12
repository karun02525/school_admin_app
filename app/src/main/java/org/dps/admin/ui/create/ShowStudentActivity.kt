package org.dps.admin.ui.create

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_assign_teacher.btnSubmit
import kotlinx.android.synthetic.main.activity_assign_teacher.progress_circular
import kotlinx.android.synthetic.main.activity_assign_teacher.sp_classes
import kotlinx.android.synthetic.main.activity_assign_teacher.sp_section
import kotlinx.android.synthetic.main.activity_show_student.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import org.dps.admin.R
import org.dps.admin.model.ClassData
import org.dps.admin.mvvm.ClassViewModel
import org.dps.admin.ui.adapter.ShowStudentsAdapter
import org.dps.admin.utils.toast
import org.koin.android.viewmodel.ext.android.viewModel

class ShowStudentActivity : AppCompatActivity() {
    private val viewModel: ClassViewModel by viewModel()
    private val mAdapter by lazy { ShowStudentsAdapter() }

    private var class_id = ""
    private var sectionName = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_student)
        hideShowProgress(true)
        setupViewModel()


        btnSubmit.setOnClickListener {
            hideShowProgress(true)
          //  viewModel.getStudentDataAsync(class_id)
        }



        btn_back.setOnClickListener { onBackPressed() }

        tv_toolbar.text="List of Students"

    }

    private fun setupViewModel() {
        viewModel.classData.observe(this, Observer {
            hideShowProgress(false)
            setSpClass(it)
        })
        viewModel.msg.observe(this, Observer {
            hideShowProgress(false)
           toast(it)
        })
        viewModel.studentsList.observe(this, Observer {
            hideShowProgress(false)
            mAdapter.list = it
            rv_show_students.adapter = mAdapter
            mAdapter.notifyDataSetChanged()
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