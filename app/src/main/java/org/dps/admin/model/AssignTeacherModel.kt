package org.dps.admin.model
import com.google.gson.annotations.SerializedName


data class AssignTeacherModel(
    @SerializedName("data")
    var `data`: AssignTeacherModelData,
    @SerializedName("status")
    var status: Boolean
)

data class AssignTeacherModelData(
    @SerializedName("class_id")
    var classId: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("status")
    var status: Int,
    @SerializedName("teacher")
    var teacher: TeacherMode?=null,
    @SerializedName("updatedAt")
    var updatedAt: String
)

data class TeacherMode(
    @SerializedName("address")
    var address: String,
    @SerializedName("certificate_doc_back")
    var certificateDocBack: String,
    @SerializedName("certificate_doc_front")
    var certificateDocFront: String,
    @SerializedName("createdAt")
    var createdAt: String,
    @SerializedName("distc")
    var distc: String,
    @SerializedName("dob")
    var dob: String,
    @SerializedName("doc_id")
    var docId: String,
    @SerializedName("document")
    var document: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("fname")
    var fname: String,
    @SerializedName("gender")
    var gender: String,
    @SerializedName("_id")
    var id: String,
    @SerializedName("lname")
    var lname: String,
    @SerializedName("mobile")
    var mobile: String,
    @SerializedName("parent_name")
    var parentName: String,
    @SerializedName("pincode")
    var pincode: String,
    @SerializedName("post_office")
    var postOffice: String,
    @SerializedName("qualification")
    var qualification: String,
    @SerializedName("state")
    var state: String,
    @SerializedName("teacher_avatar")
    var teacherAvatar: String,
    @SerializedName("teacher_doc_back")
    var teacherDocBack: String,
    @SerializedName("teacher_doc_front")
    var teacherDocFront: String,
    @SerializedName("updatedAt")
    var updatedAt: String
)