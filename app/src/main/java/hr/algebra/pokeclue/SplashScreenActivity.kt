package hr.algebra.pokeclue

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hr.algebra.pokeclue.framework.applyAnimation
import hr.algebra.pokeclue.framework.getBooleanPreference
import hr.algebra.pokeclue.framework.isOnline
import hr.algebra.pokeclue.framework.startActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*

private const val DELAY : Long = 3000
const val DATA_IMPORTED = "hr.algebra.pokeclue.data_imported"

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        startAnimations()
        redirect()
    }

    private fun startAnimations() {
        ivSplash.applyAnimation(R.anim.fly_in)
        tvSplash.applyAnimation(R.anim.visibility)
    }

    private fun redirect() {
        if (getBooleanPreference(DATA_IMPORTED)) {
            Handler(Looper.getMainLooper()).postDelayed(
                { startActivity<HostActivity>()},
                DELAY)
        } else {
            if (isOnline()) {
                Intent(this, PokeClueService::class.java).apply {
                    PokeClueService.enqueueWork(this@SplashScreenActivity, this)
                }
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.please_connect_to_the_internet),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }
}
