package org.dps.admin.ui.delete

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_delete_class.*
import kotlinx.android.synthetic.main.adapter_class.view.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import kotlinx.android.synthetic.main.dialog_delete_photos.view.*
import org.dps.admin.R
import org.dps.admin.model.ClassData
import org.dps.admin.mvvm.AssignClassTeacherViewModel
import org.dps.admin.utils.toast
import org.koin.android.viewmodel.ext.android.viewModel

class DeleteClassActivity : AppCompatActivity() {

    private val viewModel: AssignClassTeacherViewModel by viewModel()
    private val mAdapter by lazy { ClassDeleteAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_class)
        setupViewModel()
        tv_toolbar.text = "Show all Classes"
        btn_back.setOnClickListener { onBackPressed() }
        callApi()
    }


    private fun callApi() {
        hideShowProgress(true)
        viewModel.getClasses()
    }


    private fun setupViewModel() {
        viewModel.msg.observe(this, Observer {
            hideShowProgress(false)
            if(it=="successfully deleted class!") {
                callApi()
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

    fun showDialogDelete(class_id:String,className:String) {
        val dialog = LayoutInflater.from(this).inflate(R.layout.dialog_delete_photos, null)
        val mDialog = Dialog(this, R.style.MaterialDialogSheet)
        mDialog.setContentView(dialog)
        mDialog.setCancelable(true)
        mDialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        mDialog.window!!.setGravity(Gravity.CENTER)
        mDialog.show()
        dialog.btnCancel.setOnClickListener { mDialog.dismiss() }

        dialog.tvTitle.text = className
        dialog.tvMess.text = "Do you want to delete $className?"

        dialog.btnYes.setOnClickListener {
            mDialog.dismiss()
            hideShowProgress(true)
            viewModel.deleteClass(class_id)
        }
    }

    private fun hideShowProgress(flag: Boolean) {
        if (flag) progress_circular.visibility = View.VISIBLE else progress_circular.visibility =
            View.GONE
    }
}



class ClassDeleteAdapter(var list: List<ClassData> = listOf()) : RecyclerView.Adapter<ClassDeleteAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_class, parent, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bindItems(model: ClassData) {
            itemView.run {
                tvSrNo.text = "${adapterPosition + 1}"
                tvClass.text = model.name
                btnClassDelete.visibility=View.VISIBLE
                btnClassDelete.setOnClickListener {
                    (context as DeleteClassActivity).showDialogDelete(model.id,model.name)
                }
            }
        }
    }
}