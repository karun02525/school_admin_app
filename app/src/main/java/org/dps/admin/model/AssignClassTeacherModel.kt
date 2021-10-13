package org.dps.admin.model


import com.google.gson.annotations.SerializedName

data class AssignClassTeacherModel(
    @SerializedName("data")
    var `data`: List<DataModel>? = listOf(),
    @SerializedName("status")
    var status: Boolean? = false
)

data class DataModel(
    @SerializedName("class_id")
    var classId: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("status")
    var status: Int? = 0,
    @SerializedName("teacher")
    var teacher: Teacher?=null,
    @SerializedName("updatedAt")
    var updatedAt: String = ""
)

data class Teacher(
    @SerializedName("teacher_avatar")
    var avatar: String? = "",
    @SerializedName("fname")
    var fname: String? = "",
    @SerializedName("_id")
    var id: String? = "",
    @SerializedName("lname")
    var lname: String? = "",
    @SerializedName("mobile")
    var mobile: String? = ""
)