package org.dps.admin.ui.create

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_assign_teacher.progress_circular
import kotlinx.android.synthetic.main.activity_show_teacher.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import org.dps.admin.R
import org.dps.admin.mvvm.ClassViewModel
import org.dps.admin.ui.adapter.ShowTeacherAdapter
import org.dps.admin.utils.toast
import org.koin.android.viewmodel.ext.android.viewModel

class ShowTeacherActivity : AppCompatActivity() {
    private val viewModel: ClassViewModel by viewModel()
    private val mAdapter by lazy { ShowTeacherAdapter() }

    private var class_id = ""
    private var sectionName = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_teacher)
        hideShowProgress(true)
        setupViewModel()
        
        btn_back.setOnClickListener { onBackPressed() }
        tv_toolbar.text="List of Teacher"

    }

    private fun setupViewModel() {
        viewModel.msg.observe(this, Observer {
            hideShowProgress(false)
           toast(it)
        })
        viewModel.teacherList.observe(this, Observer {
            hideShowProgress(false)
            mAdapter.list = it
            rv_show_teacher.adapter = mAdapter
            mAdapter.notifyDataSetChanged()
        })
    }



    private fun hideShowProgress(flag: Boolean) {
        if (flag) progress_circular.visibility = View.VISIBLE else progress_circular.visibility =
            View.GONE
    }
}