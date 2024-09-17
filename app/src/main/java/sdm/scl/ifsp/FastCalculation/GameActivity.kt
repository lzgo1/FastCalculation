package sdm.scl.ifsp.FastCalculation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import sdm.scl.ifsp.FastCalculation.databinding.ActivityGameBinding
import sdm.scl.ifsp.FastCalculation.databinding.ActivitySettingsBinding

class GameActivity : AppCompatActivity(), OnPlayGame {
    private val activityGameBinding: ActivityGameBinding by lazy {
        ActivityGameBinding.inflate(layoutInflater)
    }
    private lateinit var settings: Settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityGameBinding.root)

        setSupportActionBar(activityGameBinding.gameTbIn.gameTb)
        //pq ele usou apply aqui //{
        supportActionBar?.title = getString(R.string.app_name)
        supportActionBar?.subtitle = getString(R.string.game)
        //}
        settings = intent.getParcelableExtra<Settings>(Extras.EXTRA_SETTINGS) ?: Settings()
        supportFragmentManager.beginTransaction()
            .replace(R.id.gameFl, WelcomeFragment.newInstance(settings)).commit()
        //Toast.makeText(this,settings.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_game, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.restartGameMi -> {
                onPlayGame()
                true
            }

            R.id.exitMi -> {
                finish()
                true
            }

            else -> {
                false
            }
        }
    }

    override fun onPlayGame() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.gameFl, GameFragment.newInstance(settings)).commit()

    }
}