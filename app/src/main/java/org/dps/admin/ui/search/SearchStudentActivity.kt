package org.dps.admin.ui.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_assign_teacher.progress_circular
import kotlinx.android.synthetic.main.activity_search_students.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import org.dps.admin.R
import org.dps.admin.mvvm.AssignClassTeacherViewModel
import org.dps.admin.ui.adapter.StudentsAdapter
import org.dps.admin.utils.toast
import org.koin.android.viewmodel.ext.android.viewModel

class SearchStudentActivity : AppCompatActivity() {
    private val viewModel: AssignClassTeacherViewModel by viewModel()
    private val mAdapter by lazy { StudentsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_students)
        hideShowProgress(true)
        setupViewModel()
        
        btn_back.setOnClickListener { onBackPressed() }
        tv_toolbar.text="List of all Students"

    }

    private fun setupViewModel() {
        viewModel.getStudents()
        viewModel.studentsList.observe(this, Observer {
            hideShowProgress(false)
            mAdapter.list = it
            rv_search_students.adapter = mAdapter
        })

        viewModel.msg.observe(this, Observer {
            hideShowProgress(false)
            toast(it)
        })
    }


    private fun hideShowProgress(flag: Boolean) {
        if (flag) progress_circular.visibility = View.VISIBLE else progress_circular.visibility =
            View.GONE
    }
}