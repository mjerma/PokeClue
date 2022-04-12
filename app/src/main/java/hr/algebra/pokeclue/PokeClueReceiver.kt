package hr.algebra.pokeclue

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import hr.algebra.pokeclue.framework.setBooleanPreference
import hr.algebra.pokeclue.framework.startActivity

class PokeClueReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        context.setBooleanPreference(DATA_IMPORTED, true)
        context.startActivity<HostActivity>()
    }
}