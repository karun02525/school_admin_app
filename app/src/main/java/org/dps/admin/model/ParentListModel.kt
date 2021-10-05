package org.dps.admin.model

import com.google.gson.annotations.SerializedName


data class ParentListModel(
        @SerializedName("data")
        var `data`: List<ParentData>?,
        @SerializedName("message")
        var message: String?,
        @SerializedName("status")
        var status: Boolean?
)
class ParentData(
        @SerializedName("address")
        var address: String? = "",
        @SerializedName("createdAt")
        var createdAt: String? = "",
        @SerializedName("distc")
        var distc: String? = "",
        @SerializedName("dob")
        var dob: String? = "",
        @SerializedName("doc_id")
        var docId: String? = "",
        @SerializedName("docoment")
        var docoment: String? = "",
        @SerializedName("email")
        var email: String? = "",
        @SerializedName("fullname")
        var fullname: String? = "",
        @SerializedName("gender")
        var gender: String? = "",
        @SerializedName("_id")
        var id: String = "",
        @SerializedName("mobile")
        var mobile: String? = "",
        @SerializedName("occupation")
        var occupation: String? = "",
        @SerializedName("pincode")
        var pincode: String? = "",
        @SerializedName("post_office")
        var postOffice: String? = "",
        @SerializedName("state")
        var state: String? = ""
) {
        override fun toString(): String {
                return  fullname.toString() +" ("+ docId.toString()+")"
        }
}


