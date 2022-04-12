package hr.algebra.pokeclue.factory

import android.content.Context
import hr.algebra.pokeclue.dao.PokemonSqlHelper

fun getPokeClueRepository(context: Context?) = PokemonSqlHelper(context)