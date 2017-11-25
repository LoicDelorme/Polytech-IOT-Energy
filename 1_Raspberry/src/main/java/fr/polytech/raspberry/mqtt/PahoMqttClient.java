package fr.polytech.raspberry.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class PahoMqttClient extends AbstractMqttClient {

    private final MqttClient mqttClient;

    private final MqttConnectOptions mqttConnectOptions;

    public PahoMqttClient(String clientId, String broker) throws Exception {
        super(clientId, broker);
        this.mqttClient = new MqttClient(broker, clientId, new MemoryPersistence());

        this.mqttConnectOptions = new MqttConnectOptions();
        this.mqttConnectOptions.setCleanSession(true);
    }

    @Override
    public void publish(String topic, String content, int qos) throws MqttException {
        final MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(qos);

        this.mqttClient.connect(this.mqttConnectOptions);
        this.mqttClient.publish(topic, message);
        this.mqttClient.disconnect();
    }
}