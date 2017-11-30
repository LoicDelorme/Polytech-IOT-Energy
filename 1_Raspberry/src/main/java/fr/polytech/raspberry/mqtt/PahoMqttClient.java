package fr.polytech.raspberry.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class PahoMqttClient extends AbstractMqttClient {

    private final MqttClient mqttClient;

    public PahoMqttClient(String clientId, String broker) throws Exception {
        super(clientId, broker);
        this.mqttClient = new MqttClient(broker, clientId, new MemoryPersistence());
    }

    @Override
    public void connect() throws MqttException {
        final MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setCleanSession(true);

        this.mqttClient.connect(mqttConnectOptions);
    }

    @Override
    public void publish(String topic, String content, int qos) throws MqttException {
        final MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(qos);

        this.mqttClient.publish(topic, message);
    }

    @Override
    public void disconnect() throws MqttException {
        this.mqttClient.disconnect();
    }
}