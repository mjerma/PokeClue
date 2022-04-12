package hr.algebra.pokeclue.framework

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import androidx.preference.PreferenceManager
import hr.algebra.pokeclue.POKECLUE_ITEM_PROVIDER_CONTENT_URI
import hr.algebra.pokeclue.POKECLUE_POKEMON_PROVIDER_CONTENT_URI
import hr.algebra.pokeclue.model.Item
import hr.algebra.pokeclue.model.Pokemon

fun View.applyAnimation(resourceId: Int)
        = startAnimation(AnimationUtils.loadAnimation(context, resourceId))

inline fun<reified T: Activity> Context.startActivity() = startActivity(Intent(this, T::class.java))

inline fun <reified T : Activity> Context.startActivity(key: String, value: String, favorites: Boolean) =
    startActivity(
        Intent(this, T::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra(key, value)
            putExtra("Favorites", favorites)
        }
    )

inline fun <reified T : Activity> Context.startActivity(key: String, value: String) =
    startActivity(
        Intent(this, T::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra(key, value)
        }
    )

inline fun<reified T: BroadcastReceiver> Context.sendBroadcast()
        = sendBroadcast(Intent(this, T::class.java))

fun Context.setBooleanPreference(key: String, value: Boolean) =
    PreferenceManager.getDefaultSharedPreferences(this)
        .edit()
        .putBoolean(key, value)
        .apply()

fun Context.getBooleanPreference(key: String) =
    PreferenceManager.getDefaultSharedPreferences(this)
        .getBoolean(key, false)

fun Context.isOnline() : Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork
    if (network != null) {
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        if (networkCapabilities != null) {
            return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        }
    }
    return false
}

fun Context.fetchPokemon() : ArrayList<Pokemon> {
    val pokemonList = ArrayList<Pokemon>()
    val cursor = contentResolver?.query(
        POKECLUE_POKEMON_PROVIDER_CONTENT_URI,
        null, null, null, null)
    if (cursor != null) {
        while(cursor.moveToNext()) {
            pokemonList.add(
                Pokemon(
                    cursor.getLong(cursor.getColumnIndex(Pokemon::_id.name)),
                    cursor.getString(cursor.getColumnIndex(Pokemon::pokemonName.name)),
                    cursor.getString(cursor.getColumnIndex(Pokemon::picturePath.name)),
                    cursor.getString(cursor.getColumnIndex(Pokemon::type.name)),
                    cursor.getInt(cursor.getColumnIndex(Pokemon::height.name)),
                    cursor.getInt(cursor.getColumnIndex(Pokemon::weight.name)),
                    cursor.getInt(cursor.getColumnIndex(Pokemon::hp.name)),
                    cursor.getInt(cursor.getColumnIndex(Pokemon::attack.name)),
                    cursor.getInt(cursor.getColumnIndex(Pokemon::defense.name)),
                    cursor.getInt(cursor.getColumnIndex(Pokemon::specialAttack.name)),
                    cursor.getInt(cursor.getColumnIndex(Pokemon::specialDefense.name)),
                    cursor.getInt(cursor.getColumnIndex(Pokemon::speed.name)),
                    cursor.getInt(cursor.getColumnIndex(Pokemon::favorite.name)) == 1
                )
            )
        }
    }
    return pokemonList
}

fun Context.fetchItems() : ArrayList<Item> {
    val itemList = ArrayList<Item>()
    val cursor = contentResolver?.query(
        POKECLUE_ITEM_PROVIDER_CONTENT_URI,
        null, null, null, null)
    if (cursor != null) {
        while(cursor.moveToNext()) {
            itemList.add(
                Item(
                    cursor.getLong(cursor.getColumnIndex(Item::_id.name)),
                    cursor.getString(cursor.getColumnIndex(Item::itemName.name)),
                    cursor.getString(cursor.getColumnIndex(Item::picturePath.name)),
                    cursor.getString(cursor.getColumnIndex(Item::description.name)),
                    cursor.getInt(cursor.getColumnIndex(Item::favorite.name)) == 1
                )
            )
        }
    }
    return itemList
}

fun ProgressBar.smoothProgress(percent: Int){
    val animation = ObjectAnimator.ofInt(this, "progress", percent)
    animation.duration = 1600
    animation.interpolator = DecelerateInterpolator()
    animation.start()
}

fun Context.isDarkThemeOn() =
    resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES