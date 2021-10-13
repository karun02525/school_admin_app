package org.dps.admin.ui.create

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_testing_dashboard.*
import org.dps.admin.R

class TestingDashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testing_dashboard)


        btnCreateParent.setOnClickListener {
            startActivity(Intent(baseContext,CreateParentActivity::class.java)
                .putExtra("source","Student")
                .putExtra("id","615c2af2b84ff4fec1c4a862")
            )
        }

        btnCreateStudent.setOnClickListener {
            startActivity(Intent(baseContext,CreateStudentActivity::class.java)
                .putExtra("source","Student")
                .putExtra("id","615c2af2b84ff4fec1c4a862")
            )
        }

        btnCreateTeacher.setOnClickListener {
            startActivity(Intent(baseContext,CreateTeacherActivity::class.java)
                .putExtra("source","Student")
                .putExtra("id","615c2af2b84ff4fec1c4a862")
            )
        }


        btnStudent.setOnClickListener {
           startActivity(Intent(baseContext,StudentsUploadDocumentsActivity::class.java)
               .putExtra("id","6165fbee8314577e9ddf75bd")
           )
        }

        btnTeacher.setOnClickListener {
            startActivity(Intent(baseContext,TeacherUploadDocumentsActivity::class.java)
                .putExtra("id","616716b42ba31655d71e2e93")
            )
        }
    }


}