package mbk.io.sabrina_hm3_m7

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mbk.io.sabrina_hm3_m7.databinding.ActivityAlarmClockBinding
import java.util.Calendar

class AlarmClockActivity : AppCompatActivity() {
    private val binding: ActivityAlarmClockBinding by lazy {
        ActivityAlarmClockBinding.inflate(layoutInflater)
    }
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSelectTime.setOnClickListener {
            val materialTimerPicker =
                MaterialTimePicker.Builder().setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(12)
                    .setMinute(10)
                    .setTitleText("SELECT TIME")
                    .build()

            materialTimerPicker.show(supportFragmentManager, "tag")

            materialTimerPicker.addOnPositiveButtonClickListener {

                val calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR_OF_DAY, materialTimerPicker.hour)
                calendar.set(Calendar.MINUTE, materialTimerPicker.minute)
                calendar.set(Calendar.SECOND, 0)

                val currentTime = Calendar.getInstance().timeInMillis
                val alarmTime = calendar.timeInMillis
                binding.tvAlarmTime.text =
                    "${materialTimerPicker.hour} : ${materialTimerPicker.minute}"
                val delayMills = alarmTime - currentTime

                if (delayMills > 0) {
                    job = CoroutineScope(Dispatchers.Main).launch {
                        delay(delayMills)
                        showToast()
                    }
                } else {
                    Toast.makeText(this, "Please enter a valid time", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun showToast() {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(this@AlarmClockActivity, "Time's up!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }
}