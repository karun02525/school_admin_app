package org.dps.admin.ui.create

import android.Manifest
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
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
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_upload_documents.*
import net.gotev.uploadservice.MultipartUploadRequest
import net.gotev.uploadservice.UploadNotificationConfig
import org.dps.admin.R
import org.dps.admin.utils.CommonMethods
import org.dps.admin.utils.SingleUploadBroadcastReceiver
import org.dps.admin.utils.toast
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class UploadDocumentsActivity : AppCompatActivity(), SingleUploadBroadcastReceiver.Delegate {


    companion object{
        const val RESULT_LOAD_IMG = 1
        const val PERMISSION_GALLERY = 1
        var checkType=""
    }


    private val uploadReceiver = SingleUploadBroadcastReceiver()
    var progress: ProgressDialog? = null
    private var imageProfileUri=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_documents)


        editPic.setOnClickListener {
            checkType="profile"
            openGallery()
        }
        tvFrontSide.setOnClickListener {
            checkType="frontSide"
            openGallery()
        }
        tvBackSide.setOnClickListener {
            checkType="backSide"
            openGallery()
        }

    }


    private fun openGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this@UploadDocumentsActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
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
            val result:CropImage.ActivityResult  = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultPath: String? = result.uri.path
                imageProfileUri = CommonMethods().compressImage(resultPath, applicationContext)
                if(checkType=="profile") {
                    Glide.with(this).load(resultPath).into(pic)
                }
                if(checkType=="frontSide") {
                    tvFrontSide.visibility=View.GONE
                    Glide.with(this).load(resultPath).into(ivFront)
                }
                if(checkType=="backSide") {
                    tvBackSide.visibility=View.GONE
                    Glide.with(this).load(resultPath).into(ivBack)
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error: Exception = result.error
                toast("Uploading photo failed! Please try again later.")
            }
        }
        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && data != null) {
            CropImage.activity(data.data)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this)
        }
    }




    fun uploadImage(imagePath: String, userId: String) {
        progress = ProgressDialog.show(this@UploadDocumentsActivity, "", resources.getString(R.string.pleaseWait), true)
        try {
            val uploadId = UUID.randomUUID().toString()
            //Creating a multi part request
            uploadReceiver.setDelegate(this)
            uploadReceiver.setUploadID(uploadId)
            MultipartUploadRequest(
                applicationContext, uploadId, "")
                .addFileToUpload(imagePath, "userMedia")
                .addParameter("userId", userId)
                .addHeader("Authorization", "")
                .setNotificationConfig(UploadNotificationConfig())
                .setMaxRetries(2)
                .setDelegate(uploadReceiver)
                .startUpload() //Starting the upload
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
                    ActivityCompat.requestPermissions(this@UploadDocumentsActivity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_GALLERY)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_GALLERY -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                println("GALLERY PERMISSION GRANTED")
                try {
                    val intent = Intent()
                    intent.type = "image/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
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
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        alertbox.show()
    }

    private fun fileChooser() {
        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickIntent.type = "image/*"
        startActivityForResult(pickIntent,RESULT_LOAD_IMG)
    }
}