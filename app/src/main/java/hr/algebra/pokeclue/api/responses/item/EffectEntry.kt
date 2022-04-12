package hr.algebra.pokeclue.api.responses.item


import com.google.gson.annotations.SerializedName

data class EffectEntry(
    @SerializedName("effect")
    val effect: String,
    @SerializedName("language")
    val language: Language,
    @SerializedName("short_effect")
    val shortEffect: String
)