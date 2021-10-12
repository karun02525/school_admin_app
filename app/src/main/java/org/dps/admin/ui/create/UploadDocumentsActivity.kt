package org.dps.admin.ui.create

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_upload_documents.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import kotlinx.android.synthetic.main.dialog_delete_photos.view.*
import net.gotev.uploadservice.MultipartUploadRequest
import net.gotev.uploadservice.UploadNotificationConfig
import net.gotev.uploadservice.UploadService
import org.dps.admin.BuildConfig
import org.dps.admin.R
import org.dps.admin.mvvm.AssignClassTeacherViewModel
import org.dps.admin.network.Const.BASE_URL
import org.dps.admin.utils.SingleUploadBroadcastReceiver
import org.dps.admin.utils.log
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

        const val fileKeyAvatar = "avatar"
        const val fileKeyParentAvatar = "parent_avatar"
        const val fileKeyFront = "doc_front_avatar"
        const val fileKeyBack = "doc_back_avatar"
    }

    private val uploadReceiver = SingleUploadBroadcastReceiver()
    private var source = ""
    private var id = ""
    var progress: ProgressDialog? = null
    private var uploadImageUrl = ""
    private var uploadDocFrontUrl = ""
    private var uploadDocBackUrl = ""
    private var documentName = ""
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
        //  tvProfileInfo.text = "1. Upload $source profile photo."
        // tvFront.text = "2. Upload $source document front photo."
        // tvBack.text = "3. Upload $source document back photo."


        apiCall()




        btn_back.setOnClickListener { onBackPressed() }


        /* btnDeleteProfile.setOnClickListener { showDialogDelete("Profile Photo", fileKeyImage) }

         btnDeleteFront.setOnClickListener {
             showDialogDelete(
                 "$documentName Front Document", fileKeyFront
             )
         }
         btnDeleteBack.setOnClickListener {
             showDialogDelete("$documentName Back Document", fileKeyBack)
         }



         */

        setupViewModel()


        initProfileClick()
        initDocumentsClick()


    }

    private fun initDocumentsClick() {

        //Upload Documets
        tvFrontSide1.setOnClickListener {
            checkType = "front_side1"
            openGallery()
        }
        tvBackSide1.setOnClickListener {
            checkType = "back_side1"
            openGallery()
        }

        tvFrontSide2.setOnClickListener {
            checkType = "front_side2"
            openGallery()
        }
        tvBackSide2.setOnClickListener {
            checkType = "back_side2"
            openGallery()
        }


        //Delete Documents
        btnDeleteFront1.setOnClickListener {
            showDialogDelete(
                "$documentName Front Document", fileKeyFront
            )
        }

        btnDeleteBack1.setOnClickListener {
            showDialogDelete(
                "$documentName Front Document", fileKeyFront
            )
        }


        btnDeleteFront2.setOnClickListener {
            showDialogDelete(
                "$documentName Front Document", fileKeyFront
            )
        }

        btnDeleteBack2.setOnClickListener {
            showDialogDelete(
                "$documentName Front Document", fileKeyFront
            )
        }


    }


    private fun initProfileClick() {

        editPic1.setOnClickListener {
            checkType = "user_profile"
            openGallery()
        }
        editPic2.setOnClickListener {
            checkType = "parent_profile"
            openGallery()
        }

        //Delete Profile
        btnDeleteProfile1.setOnClickListener {
            showDialogDelete(
                "$documentName Front Document", fileKeyFront
            )
        }
        btnDeleteProfile2.setOnClickListener {
            showDialogDelete("$documentName Back Document", fileKeyBack)
        }

    }

    private fun apiCall() {
        hideShowProgress(true)
        if (source == "Student") {
            viewModel.getStudentById(id)
        }
        if (source == "Teacher") {
            viewModel.getTeacherById(id)
        }
    }


    private fun setupViewModel() {
        viewModel.singleStudentData.observe(this, Observer {
            hideShowProgress(false)
            setUpBind(
                it.fname + " " + it.lname,
                it.mobile,
                it.avatar,
                it.docFrontAvatar,
                it.docBackAvatar,
                it.document,
                "","","","",""
            )
        })

        viewModel.deteteFilesuccess.observe(this, Observer {
            if (it != "") {
                hideShowProgress(false)
                log("deteteFilesuccess ********")
                toast(it)
                apiCall()
            }
        })

        viewModel.msg.observe(this, Observer {
            hideShowProgress(false)
            toast(it)
        })
    }


    private fun setUpBind(name: String,
                          mob: String,
                          parent_name: String,
                          status: String,
                          avatar: String,
                          parent_avatar: String,
                          student_doc_front: String,
                          student_doc_back: String,
                          parent_doc_front: String,
                          parent_doc_back: String,
                          document: String) {
        documentName = document
        //  tvFront.text = "2. Upload $source $document front photo."
        // tvBack.text = "3. Upload $source $document back photo."

        tvName.text = name
        tvMobile.text = mob
        tv_parent_name.text = parent_name
        tvStatus.text = status

        //For User Avatar
        Picasso.get()
            .load("$BASE_URL/$avatar")
            .into(iv_user_pic1, object : Callback {
                override fun onSuccess() {
                    editPic1.visibility = View.GONE
                    btnDeleteProfile1.visibility = View.VISIBLE
                }

                override fun onError(e: Exception?) {
                    editPic1.visibility = View.VISIBLE
                    btnDeleteProfile1.visibility = View.GONE
                    iv_user_pic1.setImageResource(R.drawable.profile_pic)
                }
            })


        //For Parents
        Picasso.get()
            .load("$BASE_URL/$parent_avatar")
            .into(iv_user_pic2, object : Callback {
                override fun onSuccess() {
                    editPic2.visibility = View.GONE
                    btnDeleteProfile1.visibility = View.VISIBLE
                }

                override fun onError(e: Exception?) {
                    editPic2.visibility = View.VISIBLE
                    btnDeleteProfile1.visibility = View.GONE
                    iv_user_pic2.setImageResource(R.drawable.profile_pic)
                }
            })



        //For Students Documents
        if (student_doc_front == "") {
            tvFrontSide1.visibility = View.VISIBLE
            btnDeleteFront1.visibility = View.GONE
            ivFront1.setImageResource(R.drawable.ic_placeholder_front)
        } else {
            Picasso.get()
                .load("$BASE_URL/$student_doc_front")
                .into(ivFront1, object : Callback {
                    override fun onSuccess() {
                        btnDeleteFront1.visibility = View.VISIBLE
                        tvFrontSide1.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        btnDeleteFront1.visibility = View.GONE
                        ivFront1.setImageResource(R.drawable.ic_placeholder_front)
                        tvFrontSide1.visibility = View.VISIBLE
                    }
                })
        }
        //For Student Back
        if (student_doc_back == "") {
            tvBackSide1.visibility = View.VISIBLE
            btnDeleteBack1.visibility = View.GONE
            ivBack1.setImageResource(R.drawable.ic_placeholder_front)
        } else {
            Picasso.get()
                .load("$BASE_URL/$student_doc_back")
                .into(ivBack1, object : Callback {
                    override fun onSuccess() {
                        btnDeleteBack1.visibility = View.VISIBLE
                        tvBackSide1.visibility = View.GONE
                    }
                    override fun onError(e: Exception?) {
                        btnDeleteBack1.visibility = View.GONE
                        ivBack1.setImageResource(R.drawable.ic_placeholder_front)
                        tvBackSide1.visibility = View.VISIBLE
                    }
                })
        }

        //For Parent Front
        if (parent_doc_front == "") {
            tvFrontSide2.visibility = View.VISIBLE
            btnDeleteFront2.visibility = View.GONE
            ivFront2.setImageResource(R.drawable.ic_placeholder_front)
        } else {
            Picasso.get()
                .load("$BASE_URL/$parent_doc_front")
                .into(ivFront2, object : Callback {
                    override fun onSuccess() {
                        btnDeleteFront2.visibility = View.VISIBLE
                        tvFrontSide2.visibility = View.GONE
                    }
                    override fun onError(e: Exception?) {
                        btnDeleteFront2.visibility = View.GONE
                        ivFront2.setImageResource(R.drawable.ic_placeholder_front)
                        tvFrontSide2.visibility = View.VISIBLE
                    }
                })
        }

        //For Parent Back
        if (parent_doc_back == "") {
            tvBackSide2.visibility = View.VISIBLE
            btnDeleteBack2.visibility = View.GONE
            ivBack2.setImageResource(R.drawable.ic_placeholder_front)
        } else {
            Picasso.get()
                .load("$BASE_URL/$parent_doc_back")
                .into(ivBack2, object : Callback {
                    override fun onSuccess() {
                        btnDeleteBack2.visibility = View.VISIBLE
                        tvBackSide2.visibility = View.GONE
                    }
                    override fun onError(e: Exception?) {
                        btnDeleteBack2.visibility = View.GONE
                        ivBack2.setImageResource(R.drawable.ic_placeholder_front)
                        tvBackSide2.visibility = View.VISIBLE
                    }
                })
        }
    }

    private fun showDialogDelete(title: String, fileKeySource: String) {
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

        dialog.tvTitle.text = title
        dialog.tvMess.text = "Do you want to delete ${title.lowercase(Locale.getDefault())}?"


        dialog.btnYes.setOnClickListener {
            mDialog.dismiss()
            hideShowProgress(true)
            viewModel.deleteUploadFile(id, source.lowercase(Locale.getDefault()), fileKeySource)
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
                if (checkType == "user_profile") {
                    Glide.with(this).load(resultPath).into(iv_user_pic1)
                    uploadImage(uploadImageUrl, resultPath!!, id, source.lowercase())
                }
                if (checkType == "parent_profile") {
                    Glide.with(this).load(resultPath).into(iv_user_pic2)
                    uploadImage(uploadImageUrl, resultPath!!, id, source.lowercase())
                }


                if (checkType == "front_side1") {
                    tvFrontSide1.visibility = View.GONE
                    Glide.with(this).load(resultPath).into(ivFront1)
                    uploadImageDoc(
                        uploadDocFrontUrl,
                        resultPath!!,
                        id,
                        source.lowercase(),
                        fileKeyFront
                    )
                }
                if (checkType == "back_side1") {
                    tvBackSide1.visibility = View.GONE
                    Glide.with(this).load(resultPath).into(ivBack1)
                    uploadImageDoc(
                        uploadDocBackUrl,
                        resultPath!!,
                        id,
                        source.lowercase(),
                        fileKeyBack
                    )
                }

                if (checkType == "front_side2") {
                    tvFrontSide2.visibility = View.GONE
                    Glide.with(this).load(resultPath).into(ivFront2)
                    uploadImageDoc(
                        uploadDocFrontUrl,
                        resultPath!!,
                        id,
                        source.lowercase(),
                        fileKeyFront
                    )
                }
                if (checkType == "back_side2") {
                    tvBackSide2.visibility = View.GONE
                    Glide.with(this).load(resultPath).into(ivBack2)
                    uploadImageDoc(
                        uploadDocBackUrl,
                        resultPath!!,
                        id,
                        source.lowercase(),
                        fileKeyBack
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
                .addFileToUpload(imagePath, fileKeyAvatar)
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

    private fun uploadImageDoc(
        url: String,
        imagePath: String,
        id: String,
        type: String,
        param: String
    ) {
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
        log("serverResponseBody:  $s")
        try {
            val rootObject = JSONObject(String(serverResponseBody))
            log("..............Root Response......................$rootObject")
            try {
                if (progress != null && progress!!.isShowing()) {
                    progress!!.dismiss()
                }
            } catch (error: Throwable) {
            }
            if (rootObject.getBoolean("status")) {
                apiCall()
                toast(rootObject.getString("message"))

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