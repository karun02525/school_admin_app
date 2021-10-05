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
import kotlinx.android.synthetic.main.activity_create_teacher.*
import org.dps.admin.R
import org.dps.admin.mvvm.ClassViewModel
import org.dps.admin.utils.hideSoftKeyboard
import org.dps.admin.utils.messToast
import org.dps.admin.utils.toast
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.HashMap

class CreateTeacherActivity : AppCompatActivity() {
    private val viewModel: ClassViewModel by viewModel()
    private var dob = ""
    private var postOffice = ""
    private var distc = ""
    private var state = ""
    private var country = ""
    private var gender = "Male"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_teacher)
        setupViewModel()
        setUpSpinner()

        btnSubmit.setOnClickListener {
            isCheckUI()
            //hideShowProgress(true)
            // viewModel.assignTeacherAsync(class_id,sectionName,teacher_id)
        }

        dobBtn.setOnClickListener {
            val calendar = Calendar.getInstance(Locale.getDefault())
            val datePickerDialog = DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT,
                    { _, year, month, dayOfMonth ->
                        dob = "$dayOfMonth-${month + 1}-$year"
                        dobBtn.setText(dob)
                    }, calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH])
            datePickerDialog.show()
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = group.findViewById(checkedId)
            gender = radio.text.toString()
        }

    }

    private fun setUpSpinner() {
        //----------PostOffice
        val list = arrayOf("Fakhrabad", "Kudra", "Mohania")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)
        sp_postoffice.setAdapter(adapter)
        sp_postoffice.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ ->
                    postOffice = list[position]
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

        //----------Country---
        val listCountry = arrayOf("India", "USA")
        val adapterC = ArrayAdapter(this, android.R.layout.simple_spinner_item, listCountry)
        sp_country.setAdapter(adapterC)
        sp_country.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ ->
                    country = listCountry[position]
                }


    }

    private fun isCheckUI() {
        val fname = edit_fname.text.toString()
        val lname = edit_lname.text.toString()
        val sname = edit_sname.text.toString()
        val regNo = edit_regno.text.toString()
        val qualification = edit_qualification.text.toString()
        val mobile = edit_mobile.text.toString()
        val alternate_no = edit_alternate_no.text.toString()
        val email = edit_email.text.toString()
        val address = edit_address.text.toString()
        val pincode = edit_pincode.text.toString()

        val parentFname = edit_parent_fname.text.toString()
        val parentLname = edit_parent_lname.text.toString()
        val parentSname = edit_parent_sname.text.toString()
        val parentPhone = edit_parent_phone.text.toString()
        val parentOccupation = edit_parent_occupation.text.toString()

        when {
            fname.isBlank() -> {
                mess("Please enter first name")
            }
            lname.isBlank() -> {
                mess("Please enter last name")
            }
            sname.isBlank() -> {
                mess("Please enter surname")
            }
            dob.isEmpty() -> {
                mess("Please select date of birth")
            }
            regNo.isEmpty() -> {
                mess("Please enter registration number")
            }
            qualification.isEmpty() -> {
                mess("Please enter qualification")
            }
            mobile.isEmpty() -> {
                mess("Please enter mobile no")
            }
            alternate_no.isEmpty() -> {
                mess("Please enter alternate mobile number")
            }
            email.isEmpty() -> {
                mess("Please enter email id")
            }
            address.isEmpty() -> {
                mess("Please enter address")
            }
            postOffice.isEmpty() -> {
                mess("Please enter post office")
            }
            distc.isEmpty() -> {
                mess("Please enter district")
            }
            state.isEmpty() -> {
                mess("Please enter state")
            }
            country.isEmpty() -> {
                mess("Please enter country")
            }
            pincode.isEmpty() -> {
                mess("Please enter valid pin code")
            }
            parentFname.isEmpty() -> {
                mess("Please enter first name")
            }
            parentLname.isEmpty() -> {
                mess("Please enter last name")
            }
            parentSname.isEmpty() -> {
                mess("Please enter surname")
            }
            parentPhone.isEmpty() -> {
                mess("Please enter parent phone")
            }
            parentOccupation.isEmpty() -> {
                mess("Please enter occupation")
            }
            else -> {
                val p = HashMap<String, Any>()
                p["fname"] = fname
                p["lname"] = lname
                p["surname"] = sname
                p["gender"] = gender
                p["dob"] = dob
                p["registration_no"] = regNo
                p["qualification"] = qualification
                p["phone"] = mobile
                p["alternate_no"] = alternate_no
                p["email"] = email
                p["address"] = address
                p["post_office"] = postOffice
                p["dist"] = distc
                p["state"] = state
                p["country"] = country
                p["pincode"] = pincode
                p["parent_fname"] = parentFname
                p["parent_lname"] = parentLname
                p["parent_sname"] = parentSname
                p["parent_phone"] = parentPhone
                p["parent_occupation"] = parentOccupation
                p["teacher_picture"] = ""
                hideShowProgress(true)
                viewModel.createTeacherAsync(p)
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
    }


    private fun hideShowProgress(flag: Boolean) {
        if (flag) progress_circular.visibility = View.VISIBLE else progress_circular.visibility =
                View.GONE
    }
}