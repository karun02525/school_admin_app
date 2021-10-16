package org.dps.admin.ui.assign
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_assign_rollno.sp_classes
import kotlinx.android.synthetic.main.activity_search_class_wise_students.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import org.dps.admin.R
import org.dps.admin.model.ClassData
import org.dps.admin.mvvm.AssignClassTeacherViewModel
import org.dps.admin.ui.adapter.StudentsAdapter
import org.dps.admin.utils.toast
import org.koin.android.viewmodel.ext.android.viewModel

class AssignRollNoStatusActivity : AppCompatActivity() {
    private val viewModel: AssignClassTeacherViewModel by viewModel()
    private val mAdapter by lazy { StudentsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_class_wise_students)


        btn_back.setOnClickListener { onBackPressed() }

        tv_toolbar.text="Search Roll No class wise students"

        setupViewModel()
    }

    private fun setupViewModel() {
        hideShowProgress(true)
        viewModel.getClasses()
        viewModel.classDataList.observe(this, Observer {
            hideShowProgress(false)
            setSpClass(it)
        })

        viewModel.studentsList.observe(this, Observer {
            hideShowProgress(false)
            mAdapter.list = it
            rv_stu.adapter = mAdapter
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
                hideShowProgress(true)
                viewModel.getAllStudentByClassId(data.id)
            }

    }


    private fun hideShowProgress(flag: Boolean) {
        if (flag) pb.visibility = View.VISIBLE else pb.visibility =
            View.GONE
    }
}