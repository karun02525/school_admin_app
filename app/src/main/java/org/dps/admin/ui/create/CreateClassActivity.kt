package org.dps.admin.ui.create

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_create_class.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import org.dps.admin.R
import org.dps.admin.mvvm.AssignClassTeacherViewModel
import org.dps.admin.utils.toast
import org.koin.android.viewmodel.ext.android.viewModel

class CreateClassActivity : AppCompatActivity() {
    private val viewModel: AssignClassTeacherViewModel by viewModel()
   // var sectionArray = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_class)
        setupViewModel()


        tv_toolbar.text="Create a Class"
        btn_back.setOnClickListener { onBackPressed() }


        btnSubmit.setOnClickListener {
            val msg = edit_classes.text.toString()
            when {
                msg.isEmpty() -> {
                    toast("Please enter class name")
                }
               /* sectionArray.isEmpty() -> {
                    toast("Please select one section")
                }*/
                else -> {
                    hideShowProgress(true)
                    viewModel.createClass(msg)
                }
            }
        }
    }



    private fun setupViewModel() {
        viewModel.msg.observe(this, Observer {
            hideShowProgress(false)
            edit_classes.text?.clear()
            toast(it)
        })
    }

    private fun hideShowProgress(flag: Boolean) {
        if (flag) progress_circular.visibility = View.VISIBLE else progress_circular.visibility = View.GONE
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



}
