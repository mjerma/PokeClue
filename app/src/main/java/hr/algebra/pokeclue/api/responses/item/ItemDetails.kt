package hr.algebra.pokeclue.api.responses.item


import com.google.gson.annotations.SerializedName

data class ItemDetails(
    @SerializedName("attributes")
    val attributes: List<Attribute>,
    @SerializedName("baby_trigger_for")
    val babyTriggerFor: Any,
    @SerializedName("category")
    val category: Category,
    @SerializedName("cost")
    val cost: Int,
    @SerializedName("effect_entries")
    val effectEntries: List<EffectEntry>,
    @SerializedName("flavor_text_entries")
    val flavorTextEntries: List<FlavorTextEntry>,
    @SerializedName("fling_effect")
    val flingEffect: Any,
    @SerializedName("fling_power")
    val flingPower: Any,
    @SerializedName("game_indices")
    val gameIndices: List<GameIndice>,
    @SerializedName("held_by_pokemon")
    val heldByPokemon: List<Any>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("machines")
    val machines: List<Any>,
    @SerializedName("name")
    val name: String,
    @SerializedName("names")
    val names: List<Name>,
    @SerializedName("sprites")
    val sprites: Sprites
)