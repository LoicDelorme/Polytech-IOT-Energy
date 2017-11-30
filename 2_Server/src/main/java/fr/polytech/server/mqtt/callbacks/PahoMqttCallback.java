package fr.polytech.server.mqtt.callbacks;

import fr.polytech.server.mqtt.handlers.MqttMessageHandler;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class PahoMqttCallback implements MqttCallback {

    private final MqttMessageHandler mqttMessageHandler;

    public PahoMqttCallback(MqttMessageHandler mqttMessageHandler) {
        this.mqttMessageHandler = mqttMessageHandler;
    }

    @Override
    public void connectionLost(final Throwable throwable) {
        System.exit(1);
    }

    @Override
    public void messageArrived(final String topic, final MqttMessage mqttMessage) throws Exception {
        this.mqttMessageHandler.handleMqttMessage(new String(mqttMessage.getPayload()));
    }

    @Override
    public void deliveryComplete(final IMqttDeliveryToken iMqttDeliveryToken) {

    }
}