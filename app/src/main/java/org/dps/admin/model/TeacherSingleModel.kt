package org.dps.admin.model

import com.google.gson.annotations.SerializedName

data class TeacherSingleModel(
    @SerializedName("data")
    var `data`: TeacherData?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: Boolean?
)
