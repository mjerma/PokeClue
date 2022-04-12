package hr.algebra.pokeclue.model

data class Item(
    var _id: Long?,
    val itemName: String,
    val picturePath: String,
    val description: String,
    var favorite: Boolean
)