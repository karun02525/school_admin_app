package org.dps.admin.model
import com.google.gson.annotations.SerializedName


data class StudentModel(
    @SerializedName("data")
    var `data`: List<StudentData>?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: Boolean?=false
)

data class StudentData(
    @SerializedName("fname")
    var fname: String,
    @SerializedName("_id")
    var id: String?,
    @SerializedName("lname")
    var lname: String,
    @SerializedName("rollno")
    var rollno: Int=0,
    @SerializedName("student_avatar")
    var avatar: String?,
    @SerializedName("gender")
    var gender: String?,
    @SerializedName("qualification")
    var qualification: String?,
    @SerializedName("father_name")
    var father_name: String?,
    @SerializedName("dob")
    var dob: String?,
    @SerializedName("classes")
    var classes: Class?=null,

){
    override fun toString(): String {
        var mess=""
        if(rollno==0) {
            mess = " $fname $lname "
        }
        return mess
    }
}