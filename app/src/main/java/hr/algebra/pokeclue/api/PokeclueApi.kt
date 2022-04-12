package hr.algebra.pokeclue.api

import hr.algebra.pokeclue.api.responses.item.ItemDetails
import hr.algebra.pokeclue.api.responses.itemList.ItemList
import hr.algebra.pokeclue.api.responses.pokemon.PokemonDetails
import hr.algebra.pokeclue.api.responses.pokemonlist.PokemonList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

const val API_URL = "https://pokeapi.co/api/v2/"
interface PokemonApi {

    @GET("pokemon/?limit=809")
    fun fetchPokemonList() : Call<PokemonList>

    @GET("pokemon/{name}")
    fun fetchPokemon(@Path("name") name: String) : Call<PokemonDetails>

    @GET("item/?limit=954")
    fun fetchItems() : Call<ItemList>

    @GET("item/{name}")
    fun fetchItem(@Path("name") name: String) : Call<ItemDetails>

}