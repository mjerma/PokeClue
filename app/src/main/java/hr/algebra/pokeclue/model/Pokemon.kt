package hr.algebra.pokeclue.model

data class Pokemon(
    var _id: Long?,
    val pokemonName: String,
    val picturePath: String,
    val type: String,
    val height: Int,
    val weight: Int,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int,
    var favorite: Boolean
)