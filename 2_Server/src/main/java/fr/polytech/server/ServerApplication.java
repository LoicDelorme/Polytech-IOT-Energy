package fr.polytech.server;

import fr.polytech.server.deserializers.JsonDeserializer;
import fr.polytech.server.mqtt.*;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;
import java.util.logging.Logger;

public class ServerApplication implements Runnable {

    private static int refreshRate;

    private static String defaultMqttUuid;

    private static String defaultMqttBroker;

    private static String defaultMqttTopic;

    private static int defaultMqttQos;

    private static String defaultInfluxDBHost;

    private static String defaultInfluxDBUsername;

    private static String defaultInfluxDBPassword;

    private static AbstractMqttClient defautMqttClient;

    private static Logger logger;

    {
        try (final InputStream inputStream = ServerApplication.class.getClassLoader().getResourceAsStream("configuration.properties")) {
            final Properties properties = new Properties();
            properties.load(inputStream);

            refreshRate = Integer.parseInt(properties.getProperty("fr.polytech.server.refreshRate"));
            defaultMqttUuid = properties.getProperty("fr.polytech.server.mqtt.defaultUuid");
            defaultMqttBroker = properties.getProperty("fr.polytech.server.mqtt.defaultBroker");
            defaultMqttTopic = properties.getProperty("fr.polytech.server.mqtt.defaultTopic");
            defaultMqttQos = Integer.parseInt(properties.getProperty("fr.polytech.server.mqtt.defaultQos"));

            defaultInfluxDBHost = properties.getProperty("fr.polytech.server.influxDB.defaultHost");
            defaultInfluxDBUsername = properties.getProperty("fr.polytech.server.influxDB.defaultUsername");
            defaultInfluxDBPassword = properties.getProperty("fr.polytech.server.influxDB.defaultPassword");

            final MqttMessageHandler mqttMessageHandler = new InfluxDBMqttMessageHandler(new JsonDeserializer(), defaultInfluxDBHost, defaultInfluxDBUsername, defaultInfluxDBPassword);
            defautMqttClient = new PahoMqttClient(defaultMqttUuid, defaultMqttBroker, new PahoMqttCallback(mqttMessageHandler));

            logger = Logger.getLogger(ServerApplication.class.getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run() {
        try {
            // Subscribe to the topic
            defautMqttClient.subscribe(defaultMqttTopic, defaultMqttQos);
        } catch (Exception exception) {
            final Writer writer = new StringWriter();
            exception.printStackTrace(new PrintWriter(writer));

            logger.severe(writer.toString());
        }

        while (true) {
            try {
                // Wait a bit
                Thread.sleep(refreshRate);
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