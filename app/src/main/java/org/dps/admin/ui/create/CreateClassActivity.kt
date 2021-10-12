package org.dps.admin.ui.create

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_create_class.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import kotlinx.android.synthetic.main.dialog_delete_photos.view.*
import org.dps.admin.R
import org.dps.admin.mvvm.AssignClassTeacherViewModel
import org.dps.admin.ui.adapter.ClassAdapter
import org.dps.admin.utils.toast
import org.koin.android.viewmodel.ext.android.viewModel

class CreateClassActivity : AppCompatActivity() {
    private val viewModel: AssignClassTeacherViewModel by viewModel()
    private val mAdapter by lazy { ClassAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_class)
        setupViewModel()


        tv_toolbar.text = "Create a Class"
        btn_back.setOnClickListener { onBackPressed() }
        callApi()

        btnSubmit.setOnClickListener {
            val msg = edit_classes.text.toString()
            when {
                msg.isEmpty() -> {
                    toast("Please enter class name")
                }
                else -> {
                    hideShowProgress(true)
                    viewModel.createClass(msg)
                }
            }
        }
    }

    private fun callApi() {
        hideShowProgress(true)
        viewModel.getClasses()
    }

    private fun setupViewModel() {
        viewModel.msg.observe(this, Observer {
            hideShowProgress(false)
            if(it=="successfully create a class") {
                callApi()
                edit_classes.text?.clear()
            }
            toast(it)
        })

        viewModel.classDataList.observe(this, Observer {
            hideShowProgress(false)
            if(it.isNotEmpty()) {
                mAdapter.list = it
                rv_classes.adapter = mAdapter
                mAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun hideShowProgress(flag: Boolean) {
        if (flag) progress_circular.visibility = View.VISIBLE else progress_circular.visibility =
            View.GONE
    }

}






 /*   fun clearField(){
        edit_classes.text?.clear()
        sectionArray.clear()
        checkboxA.isChecked=false
        checkboxB.isChecked=false
        checkboxC.isChecked=false
        checkboxD.isChecked=false
    }
    private fun setUpUI() {
        checkboxA.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sectionArray.add("A")
            } else {
                sectionArray.remove("A")
            }
        }
        checkboxB.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sectionArray.add("B")
            } else {
                sectionArray.remove("B")
            }
        }
        checkboxC.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sectionArray.add("C")
            } else {
                sectionArray.remove("C")
            }
        }
        checkboxD.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sectionArray.add("D")
            } else {
                sectionArray.remove("D")
            }
        }



    }*/




