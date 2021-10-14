package org.dps.admin.ui.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_assign_teacher.progress_circular
import kotlinx.android.synthetic.main.activity_search_single_teacher.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import org.dps.admin.R
import org.dps.admin.model.TeacherData
import org.dps.admin.mvvm.AssignClassTeacherViewModel
import org.dps.admin.network.Const
import org.dps.admin.utils.toast
import org.koin.android.viewmodel.ext.android.viewModel

class SearchTeacherByIdActivity : AppCompatActivity() {
    private val viewModel: AssignClassTeacherViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_single_teacher)
        setupViewModel()
        
        btn_back.setOnClickListener { onBackPressed() }
        tv_toolbar.text="Search a Teacher"


        btnSearch.setOnClickListener {
            val query=edit_search.text.toString()
            if(query.isEmpty()){
                toast("Please enter to search via mobile,doc id,email,name")
            }else {
                hideShowProgress(true)
                viewModel.getTeacherOne(query.trim())
            }
        }
    }

    private fun setupViewModel() {
        viewModel.teacherData.observe(this, Observer {
            hideShowProgress(false)
            if(it !=null){
                list_view.visibility=View.VISIBLE
                parseTeacher(it)
            }else {
                list_view.visibility=View.GONE
            }

        })

        viewModel.msg.observe(this, Observer {
            hideShowProgress(false)
            toast(it)
        })
    }

    private fun parseTeacher(it: TeacherData?) {
        it?.run {
           Picasso.get()
                .load("${Const.BASE_URL}/${teacherAvatar}")
                .into(ivProfileUser, object : Callback {
                    override fun onSuccess() {}
                    override fun onError(e: Exception?) {
                        ivProfileUser.setImageResource(R.drawable.profile_pic)
                    }
                })
            tvUserContactName.text = "$fname $lname"
            tv_mob.text = mobile
            tv_parent.text = parentName
        }
    }


    private fun hideShowProgress(flag: Boolean) {
        if (flag) progress_circular.visibility = View.VISIBLE else progress_circular.visibility =
            View.GONE
    }
}