package hr.algebra.pokeclue.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import hr.algebra.pokeclue.HostActivity
import kotlinx.android.synthetic.main.fragment_favorites.*
import hr.algebra.pokeclue.R


class FavoritesFragment : Fragment() {

    private lateinit var tabsAdapter: TabbedCollectionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initViewPager()
        initTabs()
    }

    private fun initAdapter() {
        tabsAdapter = TabbedCollectionAdapter(childFragmentManager, lifecycle)
        tabsAdapter.addFragment(Pair(PokemonFragment(), getString(R.string.pokemon)))
        tabsAdapter.addFragment(Pair(ItemsFragment(), getString(R.string.items)))
    }

    private fun initViewPager() {
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.adapter = tabsAdapter

        viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    (viewPager.adapter as TabbedCollectionAdapter)
                        .notifyItemChanged(position)
                }
            }
        )
    }

    private fun initTabs() =
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabsAdapter.getTabTitle(position)
        }.attach()

}