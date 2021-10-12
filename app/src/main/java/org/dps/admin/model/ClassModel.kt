package org.dps.admin.model
import com.google.gson.annotations.SerializedName


data class ClassModel(
    @SerializedName("data")
    var `data`: List<ClassData>? = listOf(),
    @SerializedName("message")
    var message: String? = "",
    @SerializedName("status")
    var status: Boolean? = false
)

data class ClassData(
    @SerializedName("_id")
    var id: String = "",
    @SerializedName("name")
    var name: String = ""
){
    override fun toString(): String {
        return name.toString()
    }
}