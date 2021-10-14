package org.dps.admin.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_assign_teacher.progress_circular
import kotlinx.android.synthetic.main.activity_show_teacher.*
import kotlinx.android.synthetic.main.adapter_show_teacher.view.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import org.dps.admin.R
import org.dps.admin.model.TeacherData
import org.dps.admin.mvvm.AssignClassTeacherViewModel
import org.dps.admin.network.Const
import org.dps.admin.utils.toast
import org.koin.android.viewmodel.ext.android.viewModel

class ShowTeacherActivity : AppCompatActivity() {
    private val viewModel: AssignClassTeacherViewModel by viewModel()
    private val mAdapter by lazy { ShowTeacherAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_teacher)
        hideShowProgress(true)
        setupViewModel()
        
        btn_back.setOnClickListener { onBackPressed() }
        tv_toolbar.text="List of Teacher"

    }

    private fun setupViewModel() {
        viewModel.getTeacher()
        viewModel.teacherList.observe(this, Observer {
            hideShowProgress(false)
            mAdapter.list = it
            rv_show_teacher.adapter = mAdapter
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

class ShowTeacherAdapter(var list: List<TeacherData> = listOf()) : RecyclerView.Adapter<ShowTeacherAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_show_teacher, parent, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bindItems(model: TeacherData) {
            itemView.run {
                Picasso.get()
                    .load("${Const.BASE_URL}/${model.teacherAvatar}")
                    .into(ivProfileUser, object : Callback {
                        override fun onSuccess() {}
                        override fun onError(e: Exception?) {
                            ivProfileUser.setImageResource(R.drawable.profile_pic)
                        }
                    })
                tvUserContactName.text = model.fname +" "+model.lname
                tv_mob.text = model.mobile
                tv_parent.text = model.parentName
            }
        }
    }
}