package hr.algebra.pokeclue

import android.content.*
import android.database.Cursor
import android.net.Uri
import hr.algebra.pokeclue.dao.PokemonRepository
import hr.algebra.pokeclue.factory.getPokeClueRepository
import hr.algebra.pokeclue.model.Item
import hr.algebra.pokeclue.model.Pokemon

private const val AUTHORITY = "hr.algebra.pokeclue.api.provider"
private const val POKEMON_PATH = "pokemon"
private const val ITEM_PATH = "item"

val POKECLUE_POKEMON_PROVIDER_CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$POKEMON_PATH")
val POKECLUE_ITEM_PROVIDER_CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$ITEM_PATH")

private const val POKEMON = 10
private const val POKEMON_ID = 20

private const val ITEM = 30
private const val ITEM_ID = 40


private val URI_MATCHER = with(UriMatcher(UriMatcher.NO_MATCH)) {
    addURI(AUTHORITY, POKEMON_PATH, POKEMON)
    addURI(AUTHORITY, "$POKEMON_PATH/#", POKEMON_ID)
    addURI(AUTHORITY, ITEM_PATH, ITEM)
    addURI(AUTHORITY, "$ITEM_PATH/#", ITEM_ID)
    this
}

private const val POKEMON_CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + POKEMON_PATH
private const val POKEMON_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + POKEMON_PATH

private const val ITEM_CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + ITEM_PATH
private const val ITEM_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + ITEM_PATH

class PokeClueProvider : ContentProvider() {

    private lateinit var repository: PokemonRepository

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        when(URI_MATCHER.match(uri)) {
            POKEMON -> return repository.delete(POKEMON_PATH ,selection, selectionArgs)
            POKEMON_ID -> {
                val id = uri.lastPathSegment
                if (id != null) {
                    return repository.delete(POKEMON_PATH,"${Pokemon::_id.name}=?", arrayOf(id))
                }
            }
            ITEM -> return repository.delete(ITEM_PATH ,selection, selectionArgs)
            ITEM_ID -> {
                val id = uri.lastPathSegment
                if (id != null) {
                    return repository.delete(ITEM_PATH,"${Item::_id.name}=?", arrayOf(id))
                }
            }
        }
        throw IllegalArgumentException("Wrong URI")    }

    override fun getType(uri: Uri): String? {
        when(URI_MATCHER.match(uri)) {
            POKEMON -> return POKEMON_CONTENT_DIR_TYPE
            POKEMON_ID -> return POKEMON_CONTENT_ITEM_TYPE
            ITEM -> return ITEM_CONTENT_DIR_TYPE
            ITEM_ID -> return ITEM_CONTENT_ITEM_TYPE
        }
        throw IllegalArgumentException("Wrong URI")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when(URI_MATCHER.match(uri)) {
            POKEMON -> {
                val id = repository.insert(POKEMON_PATH, values)
                return ContentUris.withAppendedId(POKECLUE_POKEMON_PROVIDER_CONTENT_URI, id)
            }
            ITEM -> {
                val id = repository.insert(ITEM_PATH, values)
                return ContentUris.withAppendedId(POKECLUE_ITEM_PROVIDER_CONTENT_URI, id)
            }
        }
        throw IllegalArgumentException("Wrong URI")
    }

    override fun onCreate(): Boolean {
        repository = getPokeClueRepository(context)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        when(URI_MATCHER.match(uri)) {
            POKEMON -> {
                return repository.query(POKEMON_PATH, projection, selection, selectionArgs, sortOrder)
            }
            ITEM -> {
                return repository.query(ITEM_PATH, projection, selection, selectionArgs, sortOrder)
            }
        }
        throw IllegalArgumentException("Wrong URI")
    }


    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        when(URI_MATCHER.match(uri)){
            POKEMON -> return repository.update(POKEMON_PATH, values, selection, selectionArgs)
            POKEMON_ID -> {
                val id = uri.lastPathSegment
                if (id != null)
                {
                    return repository.update(POKEMON_PATH, values,"${Pokemon::_id.name}=?", arrayOf(id))
                }
            }
            ITEM -> return repository.update(ITEM_PATH, values, selection, selectionArgs)
            ITEM_ID -> {
                val id = uri.lastPathSegment
                if (id != null)
                {
                    return repository.update(ITEM_PATH, values,"${Item::_id.name}=?", arrayOf(id))
                }
            }
        }
        throw IllegalArgumentException("Wrong URI")
    }

}
