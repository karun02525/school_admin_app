package org.dps.admin.ui.create

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_create_student.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import org.dps.admin.R
import org.dps.admin.model.ClassData
import org.dps.admin.mvvm.AssignClassTeacherViewModel
import org.dps.admin.utils.hideSoftKeyboard
import org.dps.admin.utils.messToast
import org.dps.admin.utils.toast
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class CreateStudentActivity : AppCompatActivity() {
    private val viewModel: AssignClassTeacherViewModel by viewModel()
    private var class_id = ""
    private var dob = ""
    private var gender = "Male"
    private var docType = ""
    private var docParentType = ""
    private var distc = ""
    private var state = ""
    private var postOffice = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_student)
        hideShowProgress(true)
        setupViewModel()

        tv_toolbar.text="Create Student"
        btn_back.setOnClickListener { onBackPressed() }


        viewModel.getClasses()
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
        sp_stu_doc_type.setAdapter(adapterDoc)
        sp_stu_doc_type.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                docType = listDocuments[position]
            }

        val adapterParentDoc = ArrayAdapter(this, android.R.layout.simple_spinner_item, listDocuments)
        sp_parent_doc_type.setAdapter(adapterParentDoc)
        sp_parent_doc_type.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                docParentType = listDocuments[position]
            }


        //----------Dist
        val listDist = arrayOf("Kaimur(Bhuabua)", "Rohtash", "Buxer", "Siwan")
        val adapterD = ArrayAdapter(this, android.R.layout.simple_spinner_item, listDist)
        sp_dist.setAdapter(adapterD)
        sp_dist.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                distc = listDist[position]
            }

        //----------State---
        val listState = arrayOf("Bihar", "UP")
        val adapterS = ArrayAdapter(this, android.R.layout.simple_spinner_item, listState)
        sp_state.setAdapter(adapterS)
        sp_state.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                state = listState[position]
            }

        //----------PostOffice
        val list = arrayOf("Fakhrabad", "Kudra", "Mohania")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)
        sp_postoffice.setAdapter(adapter)
        sp_postoffice.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                postOffice = list[position]
            }


    }


    private fun isCheckUI() {
        val fname = edit_fname.text.toString()
        val lname = edit_lname.text.toString()
        val qualification = edit_qualification.text.toString()
        val stuDocNo = edit_stu_doc_no.text.toString()
        val fatherName = edit_father_name.text.toString()
        val motherName = edit_mother_name.text.toString()
        val fatherOccupation = edit_father_occupation.text.toString()
        val motherOccupation = edit_mother_occupation.text.toString()
        val fatherQualification = edit_father_qualification.text.toString()
        val motherQualification = edit_mother_qualification.text.toString()

        val mobile = edit_mobile.text.toString()
        val email = edit_email.text.toString()
        val address = edit_address.text.toString()
        val parentDocNo = edit_parent_doc_no.text.toString()
        val pinCode = edit_pincode.text.toString()

        when {
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
            docType.isEmpty() -> {
                mess("Please enter student documents number")
            }
            stuDocNo.isEmpty() -> {
                mess("Please enter student documents number")
            }
            fatherName.isEmpty() -> {
                mess("Please full father name")
            }
            motherName.isEmpty() -> {
                mess("Please full mother name")
            }
            fatherOccupation.isEmpty() -> {
                mess("Please father occupation")
            }
            motherOccupation.isEmpty() -> {
                mess("Please mother occupation")
            }
            fatherQualification.isEmpty() -> {
                mess("Please father qualification")
            }
            motherQualification.isEmpty() -> {
                mess("Please mother qualification")
            }
            mobile.isEmpty() -> {
                mess("Please enter mobile no")
            }
            email.isEmpty() -> {
                mess("Please enter email id")
            }
            address.isEmpty() -> {
                mess("Please enter address")
            }
            state.isEmpty() -> {
                mess("Please enter state")
            }
            distc.isEmpty() -> {
                mess("Please enter district")
            }
            postOffice.isEmpty() -> {
                mess("Please enter post office")
            }
            pinCode.isEmpty() -> {
                mess("Please enter valid pin code")
            }
            docParentType.isEmpty() -> {
                mess("Please select document")
            }
            parentDocNo.isEmpty() -> {
                mess("Please enter valid document number")
            }

            else -> {
                val p = HashMap<String, Any>()
                p["classes"] = class_id
                p["fname"] = fname.trim()
                p["lname"] = lname
                p["gender"] = gender
                p["dob"] = dob
                p["qualification"] = qualification
                p["document"] = docType
                p["doc_id"] = stuDocNo
                p["father_title"] = "Mr"
                p["father_name"] = fatherName
                p["father_occupation"] = fatherOccupation
                p["father_qualification"] = fatherQualification
                p["mother_title"] = "Miss"
                p["mother_name"] = motherName
                p["mother_occupation"] = motherOccupation
                p["mother_qualification"] = motherQualification
                p["parent_document"] = docParentType
                p["parent_doc_id"] = parentDocNo.trim()
                p["mobile"] = mobile
                p["email"] = email
                p["address"] = address.trim()
                p["state"] = state
                p["distc"] = distc
                p["post_office"] = postOffice
                p["pincode"] = pinCode
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
        viewModel.id.observe(this, Observer {
            if(it!=""){
                startActivity(
                    Intent(baseContext,StudentsUploadDocumentsActivity::class.java)
                        .putExtra("id",it)
                )
            }
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


    private fun hideShowProgress(flag: Boolean) {
        if (flag) progress_circular.visibility = View.VISIBLE else progress_circular.visibility =
            View.GONE
    }
}