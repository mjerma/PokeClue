package hr.algebra.pokeclue.api

import android.content.ContentValues
import android.content.Context
import android.util.Log
import hr.algebra.nasa.handler.downloadImageAndStore
import hr.algebra.pokeclue.POKECLUE_ITEM_PROVIDER_CONTENT_URI
import hr.algebra.pokeclue.POKECLUE_POKEMON_PROVIDER_CONTENT_URI
import hr.algebra.pokeclue.PokeClueReceiver
import hr.algebra.pokeclue.api.responses.item.ItemDetails
import hr.algebra.pokeclue.api.responses.itemList.ItemList
import hr.algebra.pokeclue.api.responses.pokemon.PokemonDetails
import hr.algebra.pokeclue.api.responses.pokemonlist.PokemonList
import hr.algebra.pokeclue.framework.sendBroadcast
import hr.algebra.pokeclue.model.Item
import hr.algebra.pokeclue.model.Pokemon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokeclueFetcher(private val context: Context) {

    private var pokemonApi: PokemonApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        pokemonApi = retrofit.create(PokemonApi::class.java)
    }

    fun fetchData() {
        fetchPokemonList()
        fetchItemList()
    }

    private fun fetchPokemonList() = fetchApiData(pokemonApi.fetchPokemonList())
    private fun fetchPokemon(pokemonName: String) = fetchApiData(pokemonApi.fetchPokemon(pokemonName))
    private fun fetchItemList() = fetchApiData(pokemonApi.fetchItems())
    private fun fetchItem(itemName: String) = fetchApiData(pokemonApi.fetchItem(itemName))

    private fun <T> fetchApiData(request: Call<T>) {
        request.enqueue(
            object : Callback<T> {
                override fun onResponse(
                    call: Call<T>,
                    response: Response<T>
                ) {
                    if (response.body() != null) {
                        populateData(response.body())
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    Log.d(javaClass.name, t.message, t)
                }
            }
        )
    }

    private fun <T> populateData(data: T) =
        when (data) {
            is PokemonList -> fetchAllPokemon(data)
            is PokemonDetails -> populatePokemonData(data)
            is ItemList -> fetchAllItems(data)
            is ItemDetails -> populateItemData(data)
            else -> throw IllegalArgumentException("$data is not recognized")
        }

    private fun fetchAllItems(data: ItemList) {
        data.results.forEach { fetchItem(it.name) }
        context.sendBroadcast<PokeClueReceiver>()
    }

    private fun fetchAllPokemon(data: PokemonList) {
        data.results.forEach { fetchPokemon(it.name) }
    }

    private fun populatePokemonData(pokemonData: PokemonDetails) {
        GlobalScope.launch {

            val picturePath = downloadImageAndStore(
                context, pokemonData.sprites.other.officialArtwork.frontDefault,
                pokemonData.name.replace(" ", "_")
            )

            val values = ContentValues().apply {
                put(Pokemon::pokemonName.name, pokemonData.name)
                put(Pokemon::picturePath.name, picturePath ?: "")
                put(Pokemon::type.name, pokemonData.types.first().type.name)
                put(Pokemon::height.name, pokemonData.height)
                put(Pokemon::weight.name, pokemonData.weight)
                put(Pokemon::hp.name, pokemonData.stats[0].baseStat)
                put(Pokemon::attack.name, pokemonData.stats[1].baseStat)
                put(Pokemon::defense.name, pokemonData.stats[2].baseStat)
                put(Pokemon::specialAttack.name, pokemonData.stats[3].baseStat)
                put(Pokemon::specialDefense.name, pokemonData.stats[4].baseStat)
                put(Pokemon::speed.name, pokemonData.stats[5].baseStat)
                put(Pokemon::favorite.name, false)
            }
            context.contentResolver.insert(POKECLUE_POKEMON_PROVIDER_CONTENT_URI, values)
        }
    }

    private fun populateItemData(itemData: ItemDetails) {
        GlobalScope.launch {

            val picturePath = downloadImageAndStore(
                context, itemData.sprites.default,
                itemData.name.replace(" ", "_")
            )

            val values = ContentValues().apply {
                put(Item::itemName.name, itemData.name)
                put(Item::picturePath.name, picturePath ?: "")
                put(Item::description.name, itemData.effectEntries[0].shortEffect)
                put(Item::favorite.name, false)
            }
            context.contentResolver.insert(POKECLUE_ITEM_PROVIDER_CONTENT_URI, values)
        }
    }
}
