package fr.polytech.server.mqtt;

import org.eclipse.paho.client.mqttv3.MqttException;

public abstract class AbstractMqttClient {

    protected final String clientId;

    protected final String broker;

    public AbstractMqttClient(String clientId, String broker) {
        this.clientId = clientId;
        this.broker = broker;
    }

    public String getClientId() {
        return this.clientId;
    }

    public String getBroker() {
        return this.broker;
    }

    public abstract void connect() throws MqttException;

    public abstract void subscribe(String topic, int qos) throws MqttException;

    public abstract void disconnect() throws MqttException;
}