package org.dps.admin.ui.search

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_search_class_wise_students.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import org.dps.admin.R
import org.dps.admin.model.ClassData
import org.dps.admin.model.TeacherMode
import org.dps.admin.mvvm.AssignClassTeacherViewModel
import org.dps.admin.network.Const
import org.dps.admin.ui.adapter.StudentsAdapter
import org.dps.admin.utils.toast
import org.koin.android.viewmodel.ext.android.viewModel

class SearchClassWiseStudentsActivity : AppCompatActivity() {

    private val viewModel: AssignClassTeacherViewModel by viewModel()
    private val mAdapter by lazy { StudentsAdapter() }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_class_wise_students)


        btn_back.setOnClickListener { onBackPressed() }

        tv_toolbar.text="Search class wise students"

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
            mAdapter.notifyDataSetChanged()
        })

        viewModel.teacherAssign.observe(this, Observer {
            hideShowProgress(false)
            if(it?.teacher != null){
                teacher_view.visibility=View.VISIBLE
                parseTeacher(it.teacher)
            }else{
                teacher_view.visibility=View.GONE
            }
        })
        viewModel.msg.observe(this, Observer {
            hideShowProgress(false)
            toast(it)
        })
    }

    private fun parseTeacher(data: TeacherMode?) {
        teacher_view.visibility=View.VISIBLE
        data?.run {
            Picasso.get()
                .load("${Const.BASE_URL}/${teacherAvatar}")
                .into(ivProfileUser, object : Callback {
                    override fun onSuccess() {

                    }
                    override fun onError(e: Exception?) {
                        ivProfileUser.setImageResource(R.drawable.profile_pic)
                    }
                })
            tvUserContactName.text= "$fname $lname"
            tv_mob.text=mobile
            tv_parent.text=parentName
        }

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
                viewModel.getAssignTeacherByClassId(data.id)
            }

    }


    private fun hideShowProgress(flag: Boolean) {
        if (flag) pb.visibility = View.VISIBLE else pb.visibility =
            View.GONE
    }
}