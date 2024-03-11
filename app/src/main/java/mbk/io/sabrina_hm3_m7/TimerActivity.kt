package mbk.io.sabrina_hm3_m7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mbk.io.sabrina_hm3_m7.databinding.ActivityStopwatchBinding
import mbk.io.sabrina_hm3_m7.databinding.ActivityTimerBinding

class TimerActivity : AppCompatActivity() {
    private val binding: ActivityTimerBinding by lazy {
        ActivityTimerBinding.inflate(layoutInflater)
    }
    private var job: Job? = null
    private var running: Boolean = false
    private var totalTimeInMillis: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnStartT.setOnClickListener {
            startTimer()
        }
    }

    private fun startTimer() {
        val hours = binding.etHours.text.toString().toLongOrNull() ?: 0
        val minutes = binding.etMinutes.text.toString().toLongOrNull() ?: 0
        val seconds = binding.etSeconds.text.toString().toLongOrNull() ?: 0

        totalTimeInMillis = (hours * 3600 + minutes * 60 + seconds) * 1000

        if (totalTimeInMillis <= 0) {
            Toast.makeText(this, "Please enter a valid time", Toast.LENGTH_SHORT).show()
            return
        }

        running = true
        job = GlobalScope.launch(Dispatchers.Main) {
            while (running && totalTimeInMillis > 0) {
                delay(1000)
                totalTimeInMillis -= 1000

                val hours = totalTimeInMillis / 3600000
                val minutes = (totalTimeInMillis % 3600000) / 60000
                val seconds = (totalTimeInMillis % 60000) / 1000

                val timeLeftFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds)
                binding.tvTimer.text = timeLeftFormatted
            }

            if (totalTimeInMillis <= 0) {
                Toast.makeText(this@TimerActivity, "Time's up!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
    }

    private fun stopTimer() {
        running = false
        job?.cancel()
    }
}
