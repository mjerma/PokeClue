package hr.algebra.pokeclue.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import hr.algebra.pokeclue.BuildConfig
import hr.algebra.pokeclue.HostActivity
import hr.algebra.pokeclue.R
import hr.algebra.pokeclue.framework.isDarkThemeOn
import kotlinx.android.synthetic.main.fragment_about.*

class AboutFragment : Fragment() {

    private lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvAppVersion.text = BuildConfig.VERSION_NAME

        initSharedPreferences()
        intiThemeSwitcher()
    }

    private fun initSharedPreferences() {
        sharedPref = (activity as HostActivity).getSharedPreferences(
            getString(R.string.toggle_state),
            Context.MODE_PRIVATE
        ).also {
            with(it.edit()) {
                putBoolean(getString(R.string.toggle_state), requireContext().isDarkThemeOn())
                apply()
            }
        }
    }

    private fun intiThemeSwitcher() {
        themeSwitch.isChecked = sharedPref.getBoolean(
            getString(R.string.toggle_state),
            requireContext().isDarkThemeOn()
        )

        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            when (isChecked) {
                true -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    with(sharedPref.edit()) {
                        putBoolean(getString(R.string.toggle_state), true)
                        apply()
                    }
                }
                false -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    with(sharedPref.edit()) {
                        putBoolean(getString(R.string.toggle_state), false)
                        apply()
                    }
                }
            }
        }
    }

}