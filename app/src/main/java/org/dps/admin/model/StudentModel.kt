package org.dps.admin.model
import com.google.gson.annotations.SerializedName


data class StudentModel(
    @SerializedName("data")
    var `data`: List<StudentData>?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: String?
)

data class StudentData(
    @SerializedName("address")
    var address: String?,
    @SerializedName("class_id")
    var classId: String?,
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
    @SerializedName("father_fname")
    var fatherFname: String?,
    @SerializedName("father_lname")
    var fatherLname: String?,
    @SerializedName("father_sname")
    var fatherSname: String?,
    @SerializedName("fname")
    var fname: String?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("lname")
    var lname: String?,
    @SerializedName("mother_fname")
    var motherFname: String?,
    @SerializedName("mother_lname")
    var motherLname: String?,
    @SerializedName("mother_sname")
    var motherSname: String?,
    @SerializedName("parent_id")
    var parentId: String?,
    @SerializedName("parent_phone")
    var parentPhone: String?,
    @SerializedName("phone")
    var phone: String?,
    @SerializedName("pincode")
    var pincode: String?,
    @SerializedName("police_station")
    var policeStation: String?,
    @SerializedName("post_office")
    var postOffice: String?,
    @SerializedName("rollno")
    var rollno: String?,
    @SerializedName("state")
    var state: String?,
    @SerializedName("surname")
    var surname: String?
){
    override fun toString(): String {
        return fname.toString()  +" "+  lname.toString()
    }
}