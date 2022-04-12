package hr.algebra.pokeclue.utils

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import hr.algebra.pokeclue.R

class PokemonColorUtil(var context: Context) {

    @ColorInt
    fun getPokemonColor(typeOfPokemon: String): Int {
        val color = when (typeOfPokemon?.lowercase()) {
            "grass", "bug" -> R.color.lightTeal
            "fire" -> R.color.lightRed
            "water", "fighting", "normal" -> R.color.lightBlue
            "electric", "psychic" -> R.color.lightYellow
            "poison", "ghost" -> R.color.lightPurple
            "ground", "rock" -> R.color.lightBrown
            "dark" -> R.color.lightBlack
            else -> R.color.lightBlue
        }
        return convertColor(color)
    }

    @ColorInt
    fun convertColor(@ColorRes color: Int): Int {
        return ContextCompat.getColor(context, color)
    }
}
