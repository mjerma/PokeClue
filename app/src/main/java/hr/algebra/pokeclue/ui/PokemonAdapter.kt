package hr.algebra.pokeclue.ui

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hr.algebra.pokeclue.CURRENT_POKEMON_NAME
import hr.algebra.pokeclue.POKECLUE_POKEMON_PROVIDER_CONTENT_URI
import hr.algebra.pokeclue.PokemonPagerActivity
import hr.algebra.pokeclue.R
import hr.algebra.pokeclue.framework.startActivity
import hr.algebra.pokeclue.model.Pokemon
import hr.algebra.pokeclue.utils.PokemonColorUtil
import kotlinx.android.synthetic.main.pokemon_list_item.view.*
import java.io.File

class PokemonAdapter(
    private val items: ArrayList<Pokemon>,
    private val favorites: Boolean,
    private val context: Context
) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ivPokemon: ImageView = itemView.findViewById(R.id.ivPokemon)
        private val ivFavorite: ImageView = itemView.findViewById(R.id.ivFavorite)
        private val tvPokemonName: TextView = itemView.findViewById(R.id.tvPokemonName)
        private val tvID: TextView = itemView.findViewById(R.id.tvID)
        private val tvType: TextView = itemView.findViewById(R.id.tvType)

        fun bind(item: Pokemon) {
            val color = PokemonColorUtil(itemView.context).getPokemonColor(item.type)
            Glide.with(itemView)
                .load(item.picturePath)
                .into(ivPokemon)
            tvPokemonName.text = item.pokemonName.capitalize()
            tvType.text = item.type.capitalize()
            tvID.text = item._id.toString()
            ivFavorite.setImageResource(if (item.favorite) R.drawable.heart_active else R.drawable.heart_not_active)
            itemView.tvPokemonName.setTextColor(color)
            itemView.tvID.setTextColor(color)
            itemView.ivFavorite.setColorFilter(
                PorterDuffColorFilter(
                    color,
                    PorterDuff.Mode.SRC_ATOP
                )
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.pokemon_list_item, parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = items[position]

        holder.itemView.setOnClickListener {
            context.startActivity<PokemonPagerActivity>(
                CURRENT_POKEMON_NAME,
                items[position].pokemonName,
                favorites
            )
        }
        holder.itemView.ivFavorite.setOnClickListener {
            item.favorite = !item.favorite
            context.contentResolver.update(
                ContentUris.withAppendedId(POKECLUE_POKEMON_PROVIDER_CONTENT_URI, item._id!!),
                ContentValues().apply {
                    put(Pokemon::favorite.name, item.favorite)
                },
                null,
                null
            )
            if (favorites) {
                items.removeAt(position)
                notifyDataSetChanged()
            }
            notifyItemChanged(position)
        }
        if (!favorites) {
            holder.itemView.setOnLongClickListener {
                AlertDialog.Builder(context).apply {
                    setTitle(R.string.delete)
                    setMessage(context.getString(R.string.really_delete) + " ${item.pokemonName}?")
                    setIcon(R.drawable.delete)
                    setCancelable(true)
                    setPositiveButton("Ok", { _, _ -> deleteItem(position) })
                    setNegativeButton(context.getString(R.string.cancel), null)
                    show()
                }
                true
            }
        }
        holder.bind(item)
    }

    private fun deleteItem(position: Int) {
        val item = items[position]
        context.contentResolver.delete(
            ContentUris.withAppendedId(POKECLUE_POKEMON_PROVIDER_CONTENT_URI, item._id!!),
            null,
            null
        )
        File(item.picturePath).delete()
        items.removeAt(position)
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size
}