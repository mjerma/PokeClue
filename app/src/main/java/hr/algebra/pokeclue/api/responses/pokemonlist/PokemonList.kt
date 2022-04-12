package hr.algebra.pokeclue.api.responses.pokemonlist


import com.google.gson.annotations.SerializedName
import hr.algebra.pokeclue.api.responses.result.Result

data class PokemonList(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("previous")
    val previous: Any,
    @SerializedName("results")
    val results: List<Result>
)