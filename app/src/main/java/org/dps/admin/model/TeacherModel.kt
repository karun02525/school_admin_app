package org.dps.admin.model
import com.google.gson.annotations.SerializedName


data class TeacherModel(
    @SerializedName("data")
    var `data`: List<TeacherData>?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: String?
)

data class TeacherData(
    @SerializedName("address")
    var address: String?,
    @SerializedName("alternate_no")
    var alternateNo: String?,
    @SerializedName("country")
    var country: String?,
    @SerializedName("date")
    var date: String?,
    @SerializedName("dist")
    var dist: String?,
    @SerializedName("dob")
    var dob: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("fname")
    var fname: String?,
    @SerializedName("_id")
    var id: String?,
    @SerializedName("lname")
    var lname: String?,
    @SerializedName("parent_fname")
    var parentFname: String?,
    @SerializedName("parent_lname")
    var parentLname: String?,
    @SerializedName("parent_sname")
    var parentSname: String?,
    @SerializedName("phone")
    var phone: String?,
    @SerializedName("pincode")
    var pincode: String?,
    @SerializedName("postoffice")
    var postoffice: String?,
    @SerializedName("qualification")
    var qualification: String?,
    @SerializedName("registration_no")
    var registrationNo: String?,
    @SerializedName("state")
    var state: String?,
    @SerializedName("surname")
    var surname: String?
){
    override fun toString(): String {
        return fname.toString()  +" "+  lname.toString()
    }
}