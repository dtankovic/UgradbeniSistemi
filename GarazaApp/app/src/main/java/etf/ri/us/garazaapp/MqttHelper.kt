package etf.ri.us.garazaapp

import android.util.Log
import com.hivemq.client.mqtt.MqttClient
import com.hivemq.client.mqtt.datatypes.MqttQos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MqttHelper {

    private val client = MqttClient.builder()
        .useMqttVersion3()
        .identifier("android-client-${System.currentTimeMillis()}")
        .serverHost("broker.hivemq.com")
        .serverPort(1883)
        .automaticReconnectWithDefaultConfig()
        .buildBlocking()

    private val topic = "garaza/vrata"

    suspend fun connect(onConnected: () -> Unit = {}) {
        withContext(Dispatchers.IO) {
            try {
                client.connect()
                Log.d("MQTT", "Povezano na HiveMQ broker")
                withContext(Dispatchers.Main) { onConnected() }
            } catch (e: Exception) {
                Log.e("MQTT", "Greška pri povezivanju", e)
            }
        }
    }

    suspend fun publish(message: String) {
        withContext(Dispatchers.IO) {
            try {
                client.publishWith()
                    .topic(topic)
                    .qos(MqttQos.AT_LEAST_ONCE)
                    .payload(message.toByteArray())
                    .send()
                Log.d("MQTT", "Poruka poslata: $message")
            } catch (e: Exception) {
                Log.e("MQTT", "Greška pri slanju", e)
            }
        }
    }
}
