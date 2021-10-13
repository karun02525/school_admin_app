package org.dps.admin.model
import com.google.gson.annotations.SerializedName


data class SingleStudentModel(
    @SerializedName("data")
    var `data`: SingleStudentModelData,
    @SerializedName("message")
    var message: String,
    @SerializedName("status")
    var status: Boolean
)

data class SingleStudentModelData(
    @SerializedName("class_id")
    var classId: String,
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
    @SerializedName("father_name")
    var fatherName: String,
    @SerializedName("father_occupation")
    var fatherOccupation: String,
    @SerializedName("father_qualification")
    var fatherQualification: String,
    @SerializedName("father_title")
    var fatherTitle: String,
    @SerializedName("fname")
    var fname: String,
    @SerializedName("student_avatar")
    var student_avatar: String,
    @SerializedName("gender")
    var gender: String,
    @SerializedName("_id")
    var id: String,
    @SerializedName("lname")
    var lname: String,
    @SerializedName("mobile")
    var mobile: String,
    @SerializedName("mother_name")
    var motherName: String,
    @SerializedName("mother_occupation")
    var motherOccupation: String,
    @SerializedName("mother_qualification")
    var motherQualification: String,
    @SerializedName("mother_title")
    var motherTitle: String,
    @SerializedName("parent_avatar")
    var parentAvatar: String,
    @SerializedName("parent_doc_back")
    var parentDocBack: String,
    @SerializedName("parent_doc_front")
    var parentDocFront: String,
    @SerializedName("parent_doc_id")
    var parentDocId: String,
    @SerializedName("parent_document")
    var parentDocument: String,
    @SerializedName("pincode")
    var pincode: String,
    @SerializedName("post_office")
    var postOffice: String,
    @SerializedName("qualification")
    var qualification: String,
    @SerializedName("rollno")
    var rollno: Int,
    @SerializedName("state")
    var state: String,
    @SerializedName("student_doc_back")
    var studentDocBack: String,
    @SerializedName("student_doc_front")
    var studentDocFront: String,
    @SerializedName("updatedAt")
    var updatedAt: String,
)