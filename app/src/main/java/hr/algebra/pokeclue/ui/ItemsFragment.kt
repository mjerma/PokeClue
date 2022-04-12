package hr.algebra.pokeclue.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import hr.algebra.pokeclue.HostActivity
import hr.algebra.pokeclue.R
import hr.algebra.pokeclue.framework.fetchItems
import hr.algebra.pokeclue.model.Item
import kotlinx.android.synthetic.main.fragment_items.*
import java.util.*

class ItemsFragment : Fragment() {

    private lateinit var itemList: ArrayList<Item>
    private lateinit var filteredItemList: ArrayList<Item>
    private var favorites: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        itemList = requireContext().fetchItems()
        if (parentFragment is FavoritesFragment) {
            favorites = true
            var favoritesList = itemList.filter { it.favorite } as java.util.ArrayList<Item>
            itemList = favoritesList
        }
        filteredItemList = arrayListOf()
        filteredItemList.addAll(itemList)
        return inflater.inflate(R.layout.fragment_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemAdapter = ItemAdapter(filteredItemList, favorites, requireContext())
        rvItems.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = itemAdapter
        }
        view.clearFocus()
        if(favorites) {
            searchLayout.visibility = View.GONE
            svItems.visibility = View.GONE
        }
        svItems.setOnQueryTextListener(object  : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filteredItemList.clear()
                val searchText = newText!!.lowercase(Locale.getDefault())
                if (searchText.isNotEmpty()) {
                    itemList.forEach{
                        if (it.itemName.lowercase(Locale.getDefault()).contains(searchText)) {
                            filteredItemList.add(it)
                        }
                    }
                    itemAdapter!!.notifyDataSetChanged()
                } else {
                    filteredItemList.clear()
                    filteredItemList.addAll(itemList)
                    itemAdapter!!.notifyDataSetChanged()
                }
                return false
            }
        })
    }
}