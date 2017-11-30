package fr.polytech.server.mqtt.handlers;

public interface MqttMessageHandler {

    public void handleMqttMessage(String message);
}