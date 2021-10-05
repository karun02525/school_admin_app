package org.dps.admin.ui.create

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_create_student.*
import kotlinx.android.synthetic.main.activity_create_student.btnSubmit
import kotlinx.android.synthetic.main.activity_create_student.dobBtn
import kotlinx.android.synthetic.main.activity_create_student.edit_address
import kotlinx.android.synthetic.main.activity_create_student.edit_email
import kotlinx.android.synthetic.main.activity_create_student.edit_mobile
import kotlinx.android.synthetic.main.activity_create_student.progress_circular
import kotlinx.android.synthetic.main.activity_create_student.radioGroup
import kotlinx.android.synthetic.main.activity_create_student.sp_doc_type
import org.dps.admin.R
import org.dps.admin.model.ClassData
import org.dps.admin.model.ParentData
import org.dps.admin.mvvm.AssignClassTeacherViewModel
import org.dps.admin.utils.hideSoftKeyboard
import org.dps.admin.utils.messToast
import org.dps.admin.utils.toast
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class CreateStudentActivity : AppCompatActivity() {
    private val viewModel: AssignClassTeacherViewModel by viewModel()
    private var class_id = ""
    private var parent_id = ""
    private var dob = ""
    private var gender = "Male"
    private var docType = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_student)
        hideShowProgress(true)
        setupViewModel()



        viewModel.getClasses()
        viewModel.getParents()
        hideShowProgress(true)


        btnSubmit.setOnClickListener {
            isCheckUI()
        }

        dobBtn.setOnClickListener {
            val calendar = Calendar.getInstance(Locale.getDefault())
            val datePickerDialog = DatePickerDialog(
                this,
                AlertDialog.THEME_HOLO_LIGHT,
                { _, year, month, dayOfMonth ->
                    dob = "$dayOfMonth-${month + 1}-$year"
                    dobBtn.setText(dob)
                },
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
            )
            datePickerDialog.show()
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = group.findViewById(checkedId)
            gender = radio.text.toString()
        }

        val listDocuments = arrayOf(
            "Pan Card",
            "Aadhaar Card",
            "Voter ID Card",
            "Driving license",
            "Indian passport",
            "Ration card",
            "Birth certificate"
        )
        val adapterDoc = ArrayAdapter(this, android.R.layout.simple_spinner_item, listDocuments)
        sp_doc_type.setAdapter(adapterDoc)
        sp_doc_type.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                docType = listDocuments[position]
            }



    }


    private fun isCheckUI() {
        val fname = edit_fname.text.toString()
        val lname = edit_lname.text.toString()
        val qualification = edit_qualification.text.toString()
        val docNo = edit_doc_no.text.toString()

        when {
            parent_id.isBlank() -> {
                mess("Please select Parent")
            }
            class_id.isBlank() -> {
                mess("Please select class")
            }
            fname.isBlank() -> {
                mess("Please enter first name")
            }
            lname.isBlank() -> {
                mess("Please enter last name")
            }
            dob.isEmpty() -> {
                mess("Please select date of birth")
            }
            qualification.isEmpty() -> {
                mess("Please enter current qualification")
            }
            docNo.isEmpty() -> {
                mess("Please enter documents number")
            }
            else -> {
                val p = HashMap<String, Any>()
                p["class_id"] = class_id
                p["parent_id"] = parent_id
                p["fname"] = fname.trim()
                p["lname"] = lname
                p["gender"] = gender
                p["dob"] = dob
                p["qualification"] = qualification
                p["doc_id"] = docNo
                p["docoment"] = docType
                hideShowProgress(true)
                viewModel.createStudentAsync(p)
            }
        }
    }

    private fun mess(s: String) {
        hideSoftKeyboard()
        messToast(s)
    }

    private fun setupViewModel() {

        viewModel.parentsDataList.observe(this, Observer {
            hideShowProgress(false)
            setSpParent(it)
        })
        viewModel.classDataList.observe(this, Observer {
            hideShowProgress(false)
            setSpClass(it)
        })
        viewModel.success.observe(this, Observer {
            hideShowProgress(false)
            toast(it)
        })
        viewModel.msg.observe(this, Observer {
            hideShowProgress(false)
            toast(it)
        })
    }

    private fun setSpClass(list: List<ClassData>) {
        //Class dropdown
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_classes.setAdapter(adapter)

        sp_classes.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val data: ClassData = parent.adapter.getItem(position) as ClassData
                class_id = data.id.toString()
            }
    }

    private fun setSpParent(list: List<ParentData>) {
        //parents dropdown
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_parent.setAdapter(adapter)

        sp_parent.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val data: ParentData = parent.adapter.getItem(position) as ParentData
                parent_id = data.id
                edit_mobile.setText(data.mobile)
                edit_email.setText(data.email)
                val address=data.address +"."+ "\n"+data.postOffice +","+data.distc+","+data.state+",Pin code-"+data.pincode
                edit_address.setText(address)
            }
    }

    private fun hideShowProgress(flag: Boolean) {
        if (flag) progress_circular.visibility = View.VISIBLE else progress_circular.visibility =
            View.GONE
    }
}