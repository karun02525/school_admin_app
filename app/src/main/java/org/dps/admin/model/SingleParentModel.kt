package org.dps.admin.model
import com.google.gson.annotations.SerializedName


data class SingleParentModel(
    @SerializedName("data")
    var `data`: SingleParentModelData,
    @SerializedName("message")
    var message: String? = "",
    @SerializedName("status")
    var status: Boolean? = false
)

data class SingleParentModelData(
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
    @SerializedName("fullname")
    var fullname: String = "",
    @SerializedName("gender")
    var gender: String? = "",
    @SerializedName("_id")
    var id: String? = "",
    @SerializedName("mobile")
    var mobile: String = "",
    @SerializedName("occupation")
    var occupation: String? = "",
    @SerializedName("students")
    var students: List<Any>? = listOf(),
)