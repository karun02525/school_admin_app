package org.dps.admin.model
import com.google.gson.annotations.SerializedName


data class SingleTeacherModel(
    @SerializedName("data")
    var `data`: SingleTeacherModelData,
    @SerializedName("message")
    var message: String? = "",
    @SerializedName("status")
    var status: Boolean? = false
)

data class SingleTeacherModelData(
    @SerializedName("address")
    var address: String? = "",
    @SerializedName("avatar")
    var avatar: String? = "",
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
    @SerializedName("email")
    var email: String? = "",
    @SerializedName("fname")
    var fname: String? = "",
    @SerializedName("gender")
    var gender: String? = "",
    @SerializedName("_id")
    var id: String? = "",
    @SerializedName("lname")
    var lname: String? = "",
    @SerializedName("mobile")
    var mobile: String = "",
    @SerializedName("parent_name")
    var parentName: String? = "",
    @SerializedName("qualification")
    var qualification: String? = ""
)