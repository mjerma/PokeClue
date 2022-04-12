package hr.algebra.pokeclue

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hr.algebra.pokeclue.framework.fetchPokemon
import hr.algebra.pokeclue.model.Pokemon
import hr.algebra.pokeclue.ui.PokemonPagerAdapter
import kotlinx.android.synthetic.main.activity_pokemon_pager.*

const val CURRENT_POKEMON_NAME = "hr.algebra.pokeclue.current_pokemon_name"

class PokemonPagerActivity : AppCompatActivity() {

    private lateinit var pokemonList: ArrayList<Pokemon>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_pager)

        init()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun init() {
        val pokemonName = intent.getStringExtra(CURRENT_POKEMON_NAME) ?: ""
        val favorites = intent.getBooleanExtra("Favorites", false)
        pokemonList = fetchPokemon()
        if (favorites) {
            var favoritesList = pokemonList.filter { p -> p.favorite } as java.util.ArrayList<Pokemon>
            pokemonList = favoritesList
        }
        val pokemonPosition = pokemonList.indexOf(pokemonList.find { p -> p.pokemonName == pokemonName })
        viewPager.adapter = PokemonPagerAdapter(pokemonList, this)
        viewPager.currentItem = pokemonPosition
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}