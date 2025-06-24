package etf.ri.us.garazaapp

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val mqttHelper = MqttHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            mqttHelper.connect {
                Toast.makeText(this@MainActivity, "MQTT povezan", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.btnOpen).setOnClickListener {
            lifecycleScope.launch {
                mqttHelper.publish("open")
            }
        }

        findViewById<Button>(R.id.btnClose).setOnClickListener {
            lifecycleScope.launch {
                mqttHelper.publish("close")
            }
        }
    }
}
