package hr.algebra.pokeclue.api.responses.pokemon


import com.google.gson.annotations.SerializedName

data class MoveX(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)