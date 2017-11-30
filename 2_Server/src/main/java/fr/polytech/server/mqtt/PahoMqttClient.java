package fr.polytech.server.mqtt;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class PahoMqttClient extends AbstractMqttClient {

    private final MqttClient mqttClient;

    private final MqttCallback mqttCallback;

    public PahoMqttClient(String clientId, String broker, MqttCallback mqttCallback) throws Exception {
        super(clientId, broker);
        this.mqttClient = new MqttClient(broker, clientId, new MemoryPersistence());
        this.mqttCallback = mqttCallback;
    }

    @Override
    public void connect() throws MqttException {
        final MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setCleanSession(true);

        this.mqttClient.connect(mqttConnectOptions);
    }

    @Override
    public void subscribe(String topic, int qos) throws MqttException {
        this.mqttClient.subscribe(topic, qos);
        this.mqttClient.setCallback(this.mqttCallback);
    }

    @Override
    public void disconnect() throws MqttException {
        this.mqttClient.disconnect();
    }
}