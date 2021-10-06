package org.dps.admin.ui.create

import android.Manifest
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_upload_documents.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import net.gotev.uploadservice.MultipartUploadRequest
import net.gotev.uploadservice.UploadNotificationConfig
import net.gotev.uploadservice.UploadService
import org.dps.admin.BuildConfig
import org.dps.admin.R
import org.dps.admin.mvvm.AssignClassTeacherViewModel
import org.dps.admin.network.Const.BASE_URL
import org.dps.admin.utils.SingleUploadBroadcastReceiver
import org.dps.admin.utils.toast
import org.json.JSONException
import org.json.JSONObject
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class UploadDocumentsActivity : AppCompatActivity(), SingleUploadBroadcastReceiver.Delegate {

    private val viewModel: AssignClassTeacherViewModel by viewModel()

    companion object {
        const val RESULT_LOAD_IMG = 1
        const val PERMISSION_GALLERY = 1
        var checkType = ""
    }

    private val uploadReceiver = SingleUploadBroadcastReceiver()
    private var source = ""
    private var id = ""
    var progress: ProgressDialog? = null
    private var uploadImageUrl = ""
    private var uploadDocFrontUrl = ""
    private var uploadDocBackUrl = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_documents)

        source = intent.getStringExtra("source") ?: ""
        id = intent.getStringExtra("id") ?: ""


        UploadService.NAMESPACE = BuildConfig.APPLICATION_ID
        UploadService.NAMESPACE = "org.dps.admin"

        uploadImageUrl = "$BASE_URL/api/admin/upload-profile-pic"
        uploadDocFrontUrl = "$BASE_URL/api/admin/upload-document-front"
        uploadDocBackUrl = "$BASE_URL/api/admin/upload-document-back"

        tv_toolbar.text = "($source) Updates Profile & Documents"
        tvProfileInfo.text = "1. Upload $source $ profile photo."
        tvFront.text = "2. Upload $source document front photo."
        tvBack.text = "3. Upload $source document back photo."


        hideShowProgress(true)
        if (source == "Student") {
            viewModel.getStudentById(id)
        }
        if (source == "Parent") {
            viewModel.getParentById(id)
            editPic.visibility = View.GONE
            pic.visibility = View.GONE
            labeleds.visibility = View.GONE
            tvStatus.visibility = View.GONE
        }
        if (source == "Teacher") {
            viewModel.getTeacherById(id)
        }


        editPic.setOnClickListener {
            checkType = "profile"
            openGallery()
        }
        tvFrontSide.setOnClickListener {
            checkType = "frontSide"
            openGallery()
        }
        tvBackSide.setOnClickListener {
            checkType = "backSide"
            openGallery()
        }

        btn_back.setOnClickListener { onBackPressed() }

        setupViewModel()
    }

    override fun onDestroy() {
        viewModel.singleStudentData.value=null
        viewModel.singleParentData.value=null
        viewModel.singleTeacherData.value=null
        viewModel.msg.value=null
        super.onDestroy()
    }

    private fun setupViewModel() {

        viewModel.singleStudentData.observe(this, Observer {
            hideShowProgress(false)
            setUpBind(it.fname + " " + it.lname, "", it.avatar, it.docFrontAvatar, it.docBackAvatar,it.document)
        })

        viewModel.singleParentData.observe(this, Observer {
            hideShowProgress(false)
            setUpBind(it.fullname, it.mobile, it.avatar, it.docFrontAvatar, it.docBackAvatar,it.document)
        })

        viewModel.singleTeacherData.observe(this, Observer {
            hideShowProgress(false)
           setUpBind( it.fname + " " + it.lname, it.mobile, it.avatar, it.docFrontAvatar, it.docBackAvatar,it.document )
        })

        viewModel.msg.observe(this, Observer {
            hideShowProgress(false)
            toast(it)
        })
    }

    private fun setUpBind(
        name: String,
        mob: String,
        avatar: String?,
        frontDoc: String,
        backDoc: String,
        document:String
    ) {
        tvFront.text = "2. Upload $source $document front photo."
        tvBack.text = "3. Upload $source $document back photo."


        tvName.text = name
        if (avatar == "") {
            tvStatus.text = "pending"
        } else {
            Picasso.get()
                .load("$BASE_URL/$avatar")
                .into(pic, object : Callback {
                    override fun onSuccess() {
                        tvStatus.text = "active"
                        tvStatus.setTextColor(resources.getColor(R.color.doc_color))
                        editPic.visibility = View.GONE
                    }
                    override fun onError(e: Exception?) {
                        editPic.visibility = View.VISIBLE
                        tvStatus.text = "pending"
                        pic.setImageResource(R.drawable.profile_pic)
                    }
                })
        }

        if (mob != "") {
            tvMobile.text = mob
            tvMobile.visibility = View.VISIBLE
            labeledm.visibility = View.VISIBLE
        } else {
            tvMobile.visibility = View.GONE
            labeledm.visibility = View.GONE
        }

        if (frontDoc == "") {
            tvFrontSide.visibility = View.VISIBLE
        } else {
            Picasso.get()
                .load("$BASE_URL/$frontDoc")
                .into(ivFront, object : Callback {
                    override fun onSuccess() {
                        tvFrontSide.visibility = View.GONE
                    }
                    override fun onError(e: Exception?) {
                        ivFront.setImageResource(R.drawable.ic_placeholder_front)
                        tvFrontSide.visibility = View.VISIBLE
                    }
                })
        }

        if (backDoc == "") {
            tvBackSide.visibility = View.VISIBLE
        } else {
            Picasso.get()
                .load("$BASE_URL/$backDoc")
                .into(ivBack, object : Callback {
                    override fun onSuccess() {
                        tvBackSide.visibility = View.GONE
                    }
                    override fun onError(e: Exception?) {
                        ivBack.setImageResource(R.drawable.ic_placeholder_back)
                        tvBackSide.visibility = View.VISIBLE
                    }
                })
        }


    }

    private fun hideShowProgress(flag: Boolean) {
        if (flag) pb.visibility = View.VISIBLE else pb.visibility = View.GONE
    }

    private fun openGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                    this@UploadDocumentsActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                askPermissionGallery()
            } else {
                fileChooser()
            }
        } else {
            fileChooser()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    val result = CropImage.getActivityResult(data)
                    val resultPath: String? = result?.uri?.path
                    if (checkType == "profile") {
                        Glide.with(this).load(resultPath).into(pic)
                        uploadImage(uploadImageUrl, resultPath!!, id, source.lowercase())
                    }
                    if (checkType == "frontSide") {
                        tvFrontSide.visibility = View.GONE
                        Glide.with(this).load(resultPath).into(ivFront)
                        uploadImageDoc(
                            uploadDocFrontUrl,
                            resultPath!!,
                            id,
                            source.lowercase(),
                            "doc_front"
                        )
                    }
                    if (checkType == "backSide") {
                        tvBackSide.visibility = View.GONE
                        Glide.with(this).load(resultPath).into(ivBack)
                        uploadImageDoc(
                            uploadDocBackUrl,
                            resultPath!!,
                            id,
                            source.lowercase(),
                            "doc_back"
                        )
                    }

                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                   // val error: Exception = result.error
                    toast("Uploading photo failed! Please try again later.")
                }
            }
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && data != null) {
                CropImage.activity(data.data)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this)
            }
    }


    private fun uploadImage(url: String, imagePath: String, id: String, type: String) {
        progress = ProgressDialog(this);
        progress?.setMessage(resources.getString(R.string.pleaseWait))
        progress?.setCancelable(true)
        progress?.show()
        try {
            val uploadId = UUID.randomUUID().toString()
            uploadReceiver.setDelegate(this)
            uploadReceiver.setUploadID(uploadId)

            MultipartUploadRequest(applicationContext, uploadId, url)
                .addFileToUpload(imagePath, "image")
                .addParameter("id", id)
                .addParameter("type", type)
                // .addHeader("Authorization", "")
                .setNotificationConfig(UploadNotificationConfig())
                .setMaxRetries(2)
                .setDelegate(uploadReceiver)
                .startUpload()
        } catch (exc: Exception) {
            println("exc----- $exc")
        }
    }

    private fun uploadImageDoc(url: String, imagePath: String, id: String, type: String,param:String) {
        progress = ProgressDialog(this);
        progress?.setMessage(resources.getString(R.string.pleaseWait))
        progress?.setCancelable(true)
        progress?.show()
        try {
            val uploadId = UUID.randomUUID().toString()
            uploadReceiver.setDelegate(this)
            uploadReceiver.setUploadID(uploadId)
            MultipartUploadRequest(applicationContext, uploadId, url)
                .addFileToUpload(imagePath, param)
                .addParameter("id", id)
                .addParameter("type", type)
                // .addHeader("Authorization", "")
                .setNotificationConfig(UploadNotificationConfig())
                .setMaxRetries(2)
                .setDelegate(uploadReceiver)
                .startUpload()
        } catch (exc: Exception) {
            println("exc----- $exc")
        }
    }

    override fun onProgress(progress: Int) {
        println("progress----- $progress")
    }

    override fun onProgress(uploadedBytes: Long, totalBytes: Long) {
        println("uploadedBytes----- $uploadedBytes")
    }


    override fun onError(exception: Exception?) {
        try {
            if (exception != null) {
                progress?.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCompleted(serverResponseCode: Int, serverResponseBody: ByteArray?) {
        val s = String(serverResponseBody!!)
        Log.d("serverResponseBody", s)
        try {
            val rootObject = JSONObject(String(serverResponseBody))
            println("..............Root Response......................$rootObject")
            try {
                if (progress != null && progress!!.isShowing()) {
                    progress!!.dismiss()
                }
            } catch (error: Throwable) {
            }
            if (rootObject.getString("code") == "200") {
                val data = rootObject.getJSONObject("data")
            } else if (rootObject.getInt("code") == 400) {

            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    override fun onCancelled() {
        println("cancel image request")
    }


    // Gallery permission
    private fun askPermissionGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                    this@UploadDocumentsActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this@UploadDocumentsActivity,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                ) {
                    ActivityCompat.requestPermissions(
                        this@UploadDocumentsActivity, arrayOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ),
                        PERMISSION_GALLERY
                    )
                } else {
                    ActivityCompat.requestPermissions(
                        this@UploadDocumentsActivity,
                        arrayOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ),
                        PERMISSION_GALLERY
                    )
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_GALLERY -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                println("GALLERY PERMISSION GRANTED")
                try {
                    val intent = Intent()
                    intent.type = "image/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    val galleryIntent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(galleryIntent, RESULT_LOAD_IMG)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            } else {
                var i = 0
                val len = permissions.size
                while (i < len) {
                    val permission = permissions[i]
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        val showRationale = shouldShowRequestPermissionRationale(permission)
                        if (!showRationale) {
                            showpopupPermission()
                        } else if (Manifest.permission.READ_EXTERNAL_STORAGE == permission) {
                            askPermissionGallery()
                        }
                    }
                    i++
                }
            }
        }
    }

    private fun showpopupPermission() {
        val alertbox = AlertDialog.Builder(this@UploadDocumentsActivity)
        alertbox.setMessage("Here storage permission is required to take photo from your Gallery.")
        alertbox.setCancelable(false)
        alertbox.setPositiveButton("OK") { dialog, which ->
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", packageName, null)
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        alertbox.show()
    }

    private fun fileChooser() {
        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickIntent.type = "image/*"
        startActivityForResult(pickIntent, RESULT_LOAD_IMG)
    }
}