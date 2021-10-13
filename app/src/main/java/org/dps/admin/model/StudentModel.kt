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
    @SerializedName("class_id")
    var classId: String?,
    @SerializedName("fname")
    var fname: String?,
    @SerializedName("_id")
    var id: String?,
    @SerializedName("lname")
    var lname: String?,
    @SerializedName("rollno")
    var rollno: String?,
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

){
    override fun toString(): String {
        return fname.toString()  +" "+  lname.toString()
    }
}