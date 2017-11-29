package fr.polytech.server.mqtt;

public interface MqttMessageHandler {

    public void handleMqttMessage(String message);
}