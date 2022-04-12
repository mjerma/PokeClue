package hr.algebra.pokeclue.api.responses.itemList

import hr.algebra.pokeclue.api.responses.result.Result
import com.google.gson.annotations.SerializedName

data class ItemList(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: Any,
    @SerializedName("previous")
    val previous: Any,
    @SerializedName("results")
    val results: List<Result>
)