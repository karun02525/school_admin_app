package org.dps.admin.ui.show

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_search_class_wise_students.*
import kotlinx.android.synthetic.main.adapter_show_students.view.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import org.dps.admin.R
import org.dps.admin.model.ClassData
import org.dps.admin.model.StudentData
import org.dps.admin.model.TeacherMode
import org.dps.admin.mvvm.AssignClassTeacherViewModel
import org.dps.admin.network.Const
import org.dps.admin.utils.toast
import org.koin.android.viewmodel.ext.android.viewModel

class SearchClassWiseStudentsActivity : AppCompatActivity() {

    private val viewModel: AssignClassTeacherViewModel by viewModel()
    private val mAdapter by lazy { ShowStudentsAdapter() }



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



class ShowStudentsAdapter(var list: List<StudentData> = listOf()) : RecyclerView.Adapter<ShowStudentsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_show_students, parent, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bindItems(model: StudentData) {
            itemView.run {

                Picasso.get()
                    .load("${Const.BASE_URL}/${model.avatar}")
                    .into(ivProfileUser, object : Callback {
                        override fun onSuccess() {

                        }
                        override fun onError(e: Exception?) {
                            ivProfileUser.setImageResource(R.drawable.profile_pic)
                        }
                    })

                tvUserContactName.text = model.fname +" "+model.lname
                tv_roll_no.text = "Roll No: "+model.rollno
                tv_parent.text = model.father_name
            }
        }
    }
}