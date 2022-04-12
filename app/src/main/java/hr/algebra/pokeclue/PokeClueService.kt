package hr.algebra.pokeclue

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import hr.algebra.pokeclue.api.PokeclueFetcher

private const val JOB_ID = 1

class PokeClueService : JobIntentService() {

    override fun onHandleWork(intent: Intent) {
        PokeclueFetcher(this).fetchData()
    }

    companion object {
        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, PokeClueService::class.java, JOB_ID, intent)
        }
    }
}