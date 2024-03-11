package mbk.io.sabrina_hm3_m7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mbk.io.sabrina_hm3_m7.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnClock.setOnClickListener {
            startActivity(Intent(this, AlarmClockActivity::class.java))
        }
        binding.btnStopwatch.setOnClickListener {
            startActivity(Intent(this, StopwatchActivity::class.java))
        }
        binding.btnTimer.setOnClickListener {
            startActivity(Intent(this, TimerActivity::class.java))
        }
    }
}