package org.dps.admin.model
import com.google.gson.annotations.SerializedName


data class Suggestion(
    @SerializedName("data")
    var `data`: SuggestionData? = null,
    @SerializedName("requestId")
    var requestId: String? = null
)

data class SuggestionData(
    @SerializedName("addressList")
    var addressList: List<SuggestionAddress>? = null,
    @SerializedName("autoCompleteRequestString")
    var autoCompleteRequestString: String? = null
)

data class SuggestionAddress(
    @SerializedName("addressString")
    var addressString: String? = null,
    @SerializedName("city")
    var city: String? = null,
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("label")
    var label: String? = null,
    @SerializedName("latitude")
    var latitude: Double? = null,
    @SerializedName("longitude")
    var longitude: Double? = null,
    @SerializedName("pinCode")
    var pinCode: String? = null
)