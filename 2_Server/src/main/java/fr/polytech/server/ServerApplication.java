package fr.polytech.server;

import fr.polytech.server.deserializers.JsonDeserializer;
import fr.polytech.server.mqtt.AbstractMqttClient;
import fr.polytech.server.mqtt.PahoMqttClient;
import fr.polytech.server.mqtt.callbacks.PahoMqttCallback;
import fr.polytech.server.mqtt.handlers.InfluxDBMqttMessageHandler;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;
import java.util.logging.Logger;

public class ServerApplication implements Runnable {

    private static int defaultRefreshRateInMs;

    private static String defaultInfluxDBHost;

    private static String defaultInfluxDBUsername;

    private static String defaultInfluxDBPassword;

    private static String defaultInfluxDBDatabase;

    private static String defaultInfluxDBRetentionPolicy;

    private static String defaultMqttUuid;

    private static String defaultMqttBroker;

    private static String defaultMqttTopic;

    private static int defaultMqttQos;

    private static AbstractMqttClient defaultMqttClient;

    private static final Logger logger = Logger.getLogger(ServerApplication.class.getSimpleName());

    {
        try (final InputStream inputStream = ServerApplication.class.getClassLoader().getResourceAsStream("configuration.properties")) {
            final Properties properties = new Properties();
            properties.load(inputStream);

            defaultRefreshRateInMs = Integer.parseInt(properties.getProperty("fr.polytech.server.defaultRefreshRateInMs"));

            defaultInfluxDBHost = properties.getProperty("fr.polytech.server.influxDB.defaultHost");
            defaultInfluxDBUsername = properties.getProperty("fr.polytech.server.influxDB.defaultUsername");
            defaultInfluxDBPassword = properties.getProperty("fr.polytech.server.influxDB.defaultPassword");
            defaultInfluxDBDatabase = properties.getProperty("fr.polytech.server.influxDB.defaultDatabase");
            defaultInfluxDBRetentionPolicy = properties.getProperty("fr.polytech.server.influxDB.defaultRententionPolicy");

            defaultMqttUuid = properties.getProperty("fr.polytech.server.mqtt.defaultUuid");
            defaultMqttBroker = properties.getProperty("fr.polytech.server.mqtt.defaultBroker");
            defaultMqttTopic = properties.getProperty("fr.polytech.server.mqtt.defaultTopic");
            defaultMqttQos = Integer.parseInt(properties.getProperty("fr.polytech.server.mqtt.defaultQos"));
            defaultMqttClient = new PahoMqttClient(defaultMqttUuid, defaultMqttBroker, new PahoMqttCallback(new InfluxDBMqttMessageHandler(defaultInfluxDBHost, defaultInfluxDBUsername, defaultInfluxDBPassword, defaultInfluxDBDatabase, defaultInfluxDBRetentionPolicy, new JsonDeserializer())));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run() {
        try {
            // Attempt a connection to MQTT broker
            defaultMqttClient.connect();
        } catch (MqttException exception) {
            final Writer writer = new StringWriter();
            exception.printStackTrace(new PrintWriter(writer));

            logger.severe(writer.toString());
            System.exit(1);
        }

        try {
            // Subscribe to the topic
            defaultMqttClient.subscribe(defaultMqttTopic, defaultMqttQos);
        } catch (MqttException exception) {
            final Writer writer = new StringWriter();
            exception.printStackTrace(new PrintWriter(writer));

            logger.severe(writer.toString());
        }

        while (true) {
            try {
                // Wait a bit
                Thread.sleep(defaultRefreshRateInMs);
            } catch (Exception exception) {
                final Writer writer = new StringWriter();
                exception.printStackTrace(new PrintWriter(writer));

                logger.severe(writer.toString());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        final Thread applicationThread = new Thread(new ServerApplication());
        applicationThread.setDaemon(false);
        applicationThread.start();
    }
}