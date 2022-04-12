package hr.algebra.pokeclue.api.responses.item


import com.google.gson.annotations.SerializedName

data class LanguageX(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)