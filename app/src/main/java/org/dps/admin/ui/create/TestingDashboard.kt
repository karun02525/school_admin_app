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


        btnStudent.setOnClickListener {
           startActivity(Intent(baseContext,UploadDocumentsActivity::class.java)
               .putExtra("source","Student")
               .putExtra("id","615c2af2b84ff4fec1c4a862")
           )
        }
        btnParent.setOnClickListener {
            startActivity(Intent(baseContext,UploadDocumentsActivity::class.java)
                .putExtra("source","Parent")
                .putExtra("id","615c159a030b59a05b084636")
            )
        }
        btnTeacher.setOnClickListener {
            startActivity(Intent(baseContext,UploadDocumentsActivity::class.java)
                .putExtra("source","Teacher")
                .putExtra("id","6154575492e6d7f6500227f8")
            )
        }
    }


}