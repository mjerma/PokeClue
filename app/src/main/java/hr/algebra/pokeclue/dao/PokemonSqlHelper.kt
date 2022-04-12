package hr.algebra.pokeclue.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import hr.algebra.pokeclue.model.Item
import hr.algebra.pokeclue.model.Pokemon

private const val DB_NAME = "pokeclue.db"
private const val DB_VERSION = 1

private const val POKEMON_TABLE_NAME = "pokemon"
private const val ITEM_TABLE_NAME = "item"

private val CREATE_POKEMON_TABLE = "create table $POKEMON_TABLE_NAME( " +
        "${Pokemon::_id.name} integer primary key autoincrement, " +
        "${Pokemon::pokemonName.name} text not null, " +
        "${Pokemon::picturePath.name} text not null, " +
        "${Pokemon::type.name} text not null, " +
        "${Pokemon::height.name} integer not null, " +
        "${Pokemon::weight.name} integer not null, " +
        "${Pokemon::hp.name} integer not null, " +
        "${Pokemon::attack.name} integer not null, " +
        "${Pokemon::defense.name} integer not null, " +
        "${Pokemon::specialAttack.name} integer not null, " +
        "${Pokemon::specialDefense.name} integer not null, " +
        "${Pokemon::speed.name} integer not null, " +
        "${Pokemon::favorite.name} integer not null" + ")"

private val CREATE_ITEM_TABLE = "create table $ITEM_TABLE_NAME( " +
        "${Item::_id.name} integer primary key autoincrement, " +
        "${Item::itemName.name} text not null, " +
        "${Item::picturePath.name} text not null, " +
        "${Item::description.name} text not null, " +
        "${Item::favorite.name} text not null" + ")"

private const val DROP_POKEMON_TABLE = "drop table $POKEMON_TABLE_NAME"
private const val DROP_ITEM_TABLE = "drop table $ITEM_TABLE_NAME"

class PokemonSqlHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION),
    PokemonRepository {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_POKEMON_TABLE)
        db.execSQL(CREATE_ITEM_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_POKEMON_TABLE)
        db.execSQL(DROP_ITEM_TABLE)
        onCreate(db)
    }

    override fun delete(tableName: String?, selection: String?, selectionArgs: Array<String>?)
            = writableDatabase.delete(tableName, selection, selectionArgs)

    override fun insert(tableName: String?, values: ContentValues?) = writableDatabase.insert(tableName, null, values)

    override fun query(
        tableName: String?,
        projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? = readableDatabase
        .query(tableName,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
    )

    override fun update(
        tableName: String?,
        values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ) = writableDatabase.update(tableName, values, selection, selectionArgs)

}