package sdm.scl.ifsp.FastCalculation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import sdm.scl.ifsp.FastCalculation.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

// Definir a Toolbar como a ActionBar
        setSupportActionBar(binding.gameTbInResult.gameTb)

        // Definir o título e subtítulo
        supportActionBar?.title = getString(R.string.app_name)
        supportActionBar?.subtitle = getString(R.string.results) // Definir o subtítulo para "Resultados"


        val result = intent.getIntExtra(Extras.EXTRA_RESULT, 0)
        val settings = intent.getParcelableExtra<Settings>(Extras.EXTRA_SETTINGS)

        binding.resultTv.text = getString(R.string.result_format, result)

        binding.restartBt.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java).apply {
                putExtra(Extras.EXTRA_SETTINGS, settings)
            }
            startActivity(intent)
            finish()
        }
    }
}