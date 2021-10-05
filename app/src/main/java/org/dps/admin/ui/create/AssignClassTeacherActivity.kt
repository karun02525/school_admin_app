package org.dps.admin.ui.create

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_assign_class_teacher.*
import kotlinx.android.synthetic.main.activity_assign_teacher.*
import kotlinx.android.synthetic.main.adapter_class_teacher.view.*
import kotlinx.android.synthetic.main.bottomsheet_profile_boost.view.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import org.dps.admin.R
import org.dps.admin.model.DataModel
import org.dps.admin.model.TeacherData
import org.dps.admin.mvvm.AssignClassTeacherViewModel
import org.dps.admin.utils.toast
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AssignClassTeacherActivity : AppCompatActivity() {

    private val viewModel: AssignClassTeacherViewModel by viewModel()
    private val mAdapter by lazy { ShowClassesAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assign_class_teacher)


        btn_back.setOnClickListener { onBackPressed() }

        tv_toolbar.text="List of classes"

        setupViewModel()
    }

    private fun setupViewModel() {
        hideShowProgress(true)
        viewModel.getAssignClassTeacher()
        viewModel.teacherAssignList.observe(this, Observer {
            hideShowProgress(false)
            mAdapter.list = it
            rv_classes.adapter = mAdapter
            mAdapter.notifyDataSetChanged()
        })

        viewModel.msg.observe(this, Observer {
            hideShowProgress(false)
            if(it=="updates successfully" || it=="successfully deleted assign teacher!"){
                viewModel.getAssignClassTeacher()
                hideShowProgress(true)
            }
            toast(it)
        })


    }


    private fun hideShowProgress(flag: Boolean) {
        if (flag) pb.visibility = View.VISIBLE else pb.visibility =
            View.GONE
    }

    fun deleteAssignClassTeacher(class_id: String){
        viewModel.assignClassTeacherDelete(class_id)
    }


    fun showProfileBoostDialog(class_id: String,class_name: String) {
        viewModel.getTeacher()
        var teacher_id=""
        val dialog = LayoutInflater.from(this).inflate(R.layout.bottomsheet_profile_boost, null)
        val mDialog = Dialog(this, R.style.MaterialDialogSheet)
        mDialog.setContentView(dialog)
        mDialog.setCancelable(true)
        mDialog.window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        mDialog.window!!.setGravity(Gravity.BOTTOM)
        mDialog.show()
        dialog.closeButton.setOnClickListener { mDialog.dismiss() }

       dialog.tvClassName.text=class_name



        viewModel.teacherList.observe(this, Observer {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, it)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            dialog.sp_teacher.setAdapter(adapter)

            dialog.sp_teacher.onItemClickListener =
                AdapterView.OnItemClickListener { parent, _, position, _ ->
                    val data: TeacherData = parent.adapter.getItem(position) as TeacherData
                       teacher_id = data.id.toString()
                }
        })

        dialog.btnConfirm.setOnClickListener {
            mDialog.dismiss()
            viewModel.assignClassTeacherAsync(class_id,teacher_id)
        }

    }

}


class ShowClassesAdapter(var list: List<DataModel> = listOf()) : RecyclerView.Adapter<ShowClassesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_class_teacher, parent, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bindItems(model: DataModel) {
            itemView.run {
                tvClassname.text=model.name
                tvDate.text=convertMongoDate(model.updatedAt)

                if(model.status==0){
                    tvStatus.text="Inactive"
                    tvStatus.setTextColor(resources.getColor(R.color.red))
                }

                val teacher=model.teacher
                if(teacher==null){
                    btnApply.visibility=View.VISIBLE
                    btnDelete.visibility=View.GONE
                    lableteacher.visibility=View.GONE
                    tvTeachername.visibility=View.GONE
                }else {
                    btnApply.visibility=View.GONE
                    btnDelete.visibility=View.VISIBLE
                    lableteacher.visibility=View.VISIBLE
                    tvTeachername.visibility=View.VISIBLE
                    tvTeachername.text = model.teacher.fname + " " + model.teacher.fname
                }

                btnApply.setOnClickListener {
                    (context as  AssignClassTeacherActivity).showProfileBoostDialog(model.classId,model.name)
                }
                btnDelete.setOnClickListener {
                    (context as  AssignClassTeacherActivity).deleteAssignClassTeacher(model.classId)
                }
            }
        }

        private fun convertMongoDate(`val`: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val outputFormat = SimpleDateFormat("dd/MM/yyyy")
            try {
                val finalStr: String = outputFormat.format(inputFormat.parse(`val`))
                println(finalStr)
                return finalStr
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return "--/--/----"
        }
    }
}