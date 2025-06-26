package etf.ri.us.garazaapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1) Startaj MqttService
        startService(Intent(this, MqttService::class.java))

        // 2) Dugmad šalju intent u servis s ACTION_PUBLISH
        findViewById<Button>(R.id.btnOpen).setOnClickListener {
            Intent(this, MqttService::class.java).also { intent ->
                intent.action = MqttService.ACTION_PUBLISH
                intent.putExtra(MqttService.EXTRA_TOPIC, "garaza/vrata")
                intent.putExtra(MqttService.EXTRA_MESSAGE, "open")
                startService(intent)
            }
        }

        findViewById<Button>(R.id.btnClose).setOnClickListener {
            Intent(this, MqttService::class.java).also { intent ->
                intent.action = MqttService.ACTION_PUBLISH
                intent.putExtra(MqttService.EXTRA_TOPIC, "garaza/vrata")
                intent.putExtra(MqttService.EXTRA_MESSAGE, "close")
                startService(intent)
            }
        }
    }
}
