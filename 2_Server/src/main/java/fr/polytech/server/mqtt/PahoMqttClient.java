package fr.polytech.server.mqtt;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class PahoMqttClient extends AbstractMqttClient {

    private final MqttClient mqttClient;

    private final MqttCallback mqttCallback;

    private final MqttConnectOptions mqttConnectOptions;

    public PahoMqttClient(String clientId, String broker, MqttCallback mqttCallback) throws Exception {
        super(clientId, broker);
        this.mqttClient = new MqttClient(broker, clientId, new MemoryPersistence());
        this.mqttCallback = mqttCallback;

        this.mqttConnectOptions = new MqttConnectOptions();
        this.mqttConnectOptions.setCleanSession(true);
    }

    @Override
    public void subscribe(String topic, int qos) throws MqttException {
        this.mqttClient.connect(this.mqttConnectOptions);
        this.mqttClient.subscribe(topic, qos);
        this.mqttClient.setCallback(this.mqttCallback);
    }
}