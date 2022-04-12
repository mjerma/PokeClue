package hr.algebra.pokeclue.api.responses.item


import com.google.gson.annotations.SerializedName

data class FlavorTextEntry(
    @SerializedName("language")
    val language: LanguageX,
    @SerializedName("text")
    val text: String,
    @SerializedName("version_group")
    val versionGroup: VersionGroup
)