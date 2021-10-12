package org.dps.admin.model
import com.google.gson.annotations.SerializedName


data class SingleStudentModel(
    @SerializedName("data")
    var `data`: SingleStudentModelData,
    @SerializedName("message")
    var message: String? = "",
    @SerializedName("status")
    var status: Boolean? = false
)

data class SingleStudentModelData(
    @SerializedName("avatar")
    var avatar: String = "",
    @SerializedName("mobile")
    var mobile: String = "",
    @SerializedName("class_id")
    var classId: String? = "",
    @SerializedName("dob")
    var dob: String? = "",
    @SerializedName("doc_back_avatar")
    var docBackAvatar: String = "",
    @SerializedName("doc_front_avatar")
    var docFrontAvatar: String = "",
    @SerializedName("doc_id")
    var docId: String? = "",
    @SerializedName("document")
    var document: String = "",
    @SerializedName("fname")
    var fname: String? = "",
    @SerializedName("gender")
    var gender: String? = "",
    @SerializedName("_id")
    var id: String? = "",
    @SerializedName("lname")
    var lname: String? = "",
    @SerializedName("parent")
    var parent: String? = "",
    @SerializedName("qualification")
    var qualification: String? = "",
    @SerializedName("rollno")
    var rollno: Int? = 0,

)