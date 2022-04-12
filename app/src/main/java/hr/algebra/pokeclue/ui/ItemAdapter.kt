package hr.algebra.pokeclue.ui

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hr.algebra.pokeclue.POKECLUE_ITEM_PROVIDER_CONTENT_URI
import hr.algebra.pokeclue.R
import hr.algebra.pokeclue.model.Item
import kotlinx.android.synthetic.main.pokemon_list_item.view.*
import java.io.File

class  ItemAdapter(private val items: ArrayList<Item>, private val favorites: Boolean, private val context: Context)
    : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ivItem: ImageView = itemView.findViewById(R.id.ivItem)
        private val ivFavorite: ImageView = itemView.findViewById(R.id.ivFavorite)
        private val tvItemName: TextView = itemView.findViewById(R.id.tvItemName)
        private val tvID: TextView = itemView.findViewById(R.id.tvID)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)

        fun bind(item: Item) {
            Glide.with(itemView)
                .load(item.picturePath)
                .into(ivItem)
            tvItemName.text = item.itemName.capitalize()
            tvDescription.text = item.description
            tvID.text = item._id.toString()
            ivFavorite.setImageResource(if (item.favorite) R.drawable.heart_active else R.drawable.heart_not_active)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item, parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = items[position]

        holder.itemView.ivFavorite.setOnClickListener {
            item.favorite = !item.favorite
            context.contentResolver.update(
                ContentUris.withAppendedId(POKECLUE_ITEM_PROVIDER_CONTENT_URI, item._id!!),
                ContentValues().apply {
                    put(Item::favorite.name, item.favorite)
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

        holder.itemView.setOnLongClickListener{
            AlertDialog.Builder(context).apply {
                setTitle(R.string.delete)
                setMessage(context.getString(R.string.really_delete) + " ${item.itemName}?")
                setIcon(R.drawable.delete)
                setCancelable(true)
                setPositiveButton("Ok", {_, _ -> deleteItem(position)})
                setNegativeButton(context.getString(R.string.cancel), null)
                show()
            }
            true
        }
        holder.bind(item)
    }

    private fun deleteItem(position: Int) {
        val item = items[position]
        // cp:items/1
        context.contentResolver.delete(
            ContentUris.withAppendedId(POKECLUE_ITEM_PROVIDER_CONTENT_URI, item._id!!), null, null)
        File(item.picturePath).delete()
        items.removeAt(position)
        notifyDataSetChanged()
    }

    override fun getItemCount() =  items.size
}