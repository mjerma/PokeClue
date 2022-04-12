package hr.algebra.pokeclue.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabbedCollectionAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragments: MutableList<Pair<Fragment, String>> = mutableListOf()

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position].first

    fun addFragment(fragmentPair: Pair<Fragment, String>) = fragments.add(fragmentPair)

    fun getTabTitle(position: Int) = fragments[position].second
}
