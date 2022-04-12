package hr.algebra.pokeclue.api.responses.item


import com.google.gson.annotations.SerializedName

data class Generation(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)