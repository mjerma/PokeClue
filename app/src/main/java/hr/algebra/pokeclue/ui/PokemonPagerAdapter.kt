package hr.algebra.pokeclue.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hr.algebra.pokeclue.POKEMON_NAME
import hr.algebra.pokeclue.R
import hr.algebra.pokeclue.WebViewActivity
import hr.algebra.pokeclue.framework.smoothProgress
import hr.algebra.pokeclue.framework.startActivity
import hr.algebra.pokeclue.model.Pokemon
import kotlinx.android.synthetic.main.pokemon_pager.view.*

class PokemonPagerAdapter(private val items: ArrayList<Pokemon>, private val context: Context)
    : RecyclerView.Adapter<PokemonPagerAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val ivPokemon: ImageView = itemView.findViewById(R.id.ivPokemon)
        private val tvPokemonName: TextView = itemView.findViewById(R.id.tvPokemonName)
        private val tvType: TextView = itemView.findViewById(R.id.tvType)
        private val tvHP: TextView = itemView.findViewById(R.id.tvHP)
        private val tvAttack: TextView = itemView.findViewById(R.id.tvAttack)
        private val tvDefense: TextView = itemView.findViewById(R.id.tvDefense)
        private val tvSpAtk: TextView = itemView.findViewById(R.id.tvSpAtk)
        private val tvSpDef: TextView = itemView.findViewById(R.id.tvSpDef)
        private val tvSpeed: TextView = itemView.findViewById(R.id.tvSpeed)

        private val pbHP: ProgressBar = itemView.findViewById(R.id.pbHP)
        private val pbAttack: ProgressBar = itemView.findViewById(R.id.pbAttack)
        private val pbDefense: ProgressBar = itemView.findViewById(R.id.pbDefense)
        private val pbSpAtk: ProgressBar = itemView.findViewById(R.id.pbSpAtk)
        private val pbSpDef: ProgressBar = itemView.findViewById(R.id.pbSpDef)
        private val pbSpeed: ProgressBar = itemView.findViewById(R.id.pbSpeed)

        fun bind(item: Pokemon) {
            Glide.with(itemView)
                .load(item.picturePath)
                .into(ivPokemon)
            tvPokemonName.text = item.pokemonName.capitalize()
            tvType.text = item.type.uppercase()
            tvHP.text = item.hp.toString()
            tvAttack.text = item.attack.toString()
            tvDefense.text = item.defense.toString()
            tvSpAtk.text = item.specialAttack.toString()
            tvSpDef.text = item.specialDefense.toString()
            tvSpeed.text = item.speed.toString()

            pbHP.smoothProgress(item.hp)
            pbAttack.smoothProgress(item.attack)
            pbDefense.smoothProgress(item.defense)
            pbSpAtk.smoothProgress(item.specialAttack)
            pbSpDef.smoothProgress(item.specialDefense)
            pbSpeed.smoothProgress(item.speed)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.pokemon_pager, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.btnDetails.setOnClickListener {
            context.startActivity<WebViewActivity>(POKEMON_NAME, items[position].pokemonName)
        }
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}