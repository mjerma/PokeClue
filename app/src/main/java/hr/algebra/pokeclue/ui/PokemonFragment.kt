package hr.algebra.pokeclue.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import hr.algebra.pokeclue.HostActivity
import hr.algebra.pokeclue.R
import hr.algebra.pokeclue.framework.fetchPokemon
import hr.algebra.pokeclue.model.Pokemon
import kotlinx.android.synthetic.main.fragment_pokemon.*
import java.util.*

class PokemonFragment : Fragment() {

    private lateinit var pokemonList: ArrayList<Pokemon>
    private lateinit var filteredPokemonList: ArrayList<Pokemon>
    private var favorites: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pokemonList = requireContext().fetchPokemon()
        if (parentFragment is FavoritesFragment) {
            favorites = true
            var favoritesList = pokemonList.filter { it.favorite } as ArrayList<Pokemon>
            pokemonList = favoritesList
        }
        filteredPokemonList = arrayListOf()
        filteredPokemonList.addAll(pokemonList)
        return inflater.inflate(R.layout.fragment_pokemon, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pokemonAdapter = PokemonAdapter(filteredPokemonList, favorites, requireContext())
        rvPokemon.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = pokemonAdapter
        }

        rvPokemon.setHasFixedSize(true);
        rvPokemon.setItemViewCacheSize(20);
        rvPokemon.setDrawingCacheEnabled(true);

        if(favorites) {
            searchLayout.visibility = View.GONE
            svPokemon.visibility = View.GONE
        }
        view.clearFocus()
        svPokemon.setOnQueryTextListener(object  : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filteredPokemonList.clear()
                val searchText = newText!!.lowercase(Locale.getDefault())
                if (searchText.isNotEmpty()) {
                    pokemonList.forEach{
                        if (it.pokemonName.lowercase(Locale.getDefault()).contains(searchText)) {
                            filteredPokemonList.add(it)
                        }
                    }
                    pokemonAdapter!!.notifyDataSetChanged()
                } else {
                    filteredPokemonList.clear()
                    filteredPokemonList.addAll(pokemonList)
                    pokemonAdapter!!.notifyDataSetChanged()
                }
                return false
            }
        })
    }
}