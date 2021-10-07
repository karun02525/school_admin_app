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
import kotlinx.android.synthetic.main.activity_create_teacher.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import org.dps.admin.R
import org.dps.admin.mvvm.AssignClassTeacherViewModel
import org.dps.admin.utils.hideSoftKeyboard
import org.dps.admin.utils.messToast
import org.dps.admin.utils.toast
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class CreateTeacherActivity : AppCompatActivity() {
    private val viewModel: AssignClassTeacherViewModel by viewModel()
    private var dob = ""
    private var gender = "Male"
    private var docType = ""
    private var distc = ""
    private var state = ""
    private var postOffice = ""
    private var sourceTitleName="Teacher"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_teacher)
        setupViewModel()


        btn_back.setOnClickListener { onBackPressed() }

        tv_toolbar.text="Create $sourceTitleName"



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


        setUpSpinner()
    }

    private fun setUpSpinner() {

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
        val mobile = edit_mobile.text.toString()
        val email = edit_email.text.toString()
        val address = edit_address.text.toString()
        val qualification = edit_qualification.text.toString()
        val docNo = edit_doc_no.text.toString()
        val parentName = edit_parent_name.text.toString()
        val pinCode = edit_pincode.text.toString()


        when {
            fname.isBlank() -> {
                mess("Please enter first name")
            }
            lname.isBlank() -> {
                mess("Please enter last name")
            }
            dob.isEmpty() -> {
                mess("Please select date of birth")
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
            qualification.isEmpty() -> {
                mess("Please enter qualification")
            }
            parentName.isEmpty() -> {
                mess("Please enter parent name")
            }
            docNo.isEmpty() -> {
                mess("Please enter documents number")
            }

            else -> {
                val p = HashMap<String, Any>()
                p["fname"] = fname
                p["lname"] = lname
                p["gender"] = gender
                p["dob"] = dob
                p["mobile"] = mobile
                p["email"] = email
                p["address"] = address.trim()
                p["qualification"] = qualification.trim()
                p["parent_name"] = parentName
                p["doc_id"] = docNo
                p["document"] = docType
                p["state"] = state
                p["distc"] = distc
                p["post_office"] = postOffice
                p["pincode"] = pinCode
                hideShowProgress(true)
                viewModel.createTeacher(p)
            }
        }
    }

    private fun mess(s: String) {
        hideSoftKeyboard()
        messToast(s)
    }

    private fun setupViewModel() {
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
                    Intent(baseContext,UploadDocumentsActivity::class.java)
                        .putExtra("source",sourceTitleName)
                        .putExtra("id",it)
                )
            }
        })
    }


    private fun hideShowProgress(flag: Boolean) {
        if (flag) progress_circular.visibility = View.VISIBLE else progress_circular.visibility =
            View.GONE
    }
}