package fr.polytech.raspberry;

import fr.polytech.raspberry.deserializers.Deserializer;
import fr.polytech.raspberry.deserializers.JsonDeserializer;
import fr.polytech.raspberry.entities.Energy;
import fr.polytech.raspberry.mqtt.AbstractMqttClient;
import fr.polytech.raspberry.mqtt.PahoMqttClient;
import fr.polytech.raspberry.restful.RestfulRequester;
import fr.polytech.raspberry.restful.StringRestfulRequester;
import fr.polytech.raspberry.serializers.JsonSerializer;
import fr.polytech.raspberry.serializers.Serializer;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;
import java.util.logging.Logger;

public class RaspberryApplication implements Runnable {

    private static String defaultWebServiceBaseUrl;

    private static String defaultWebServiceResourcePathUrl;

    private static int defaultWebServiceRefreshRateInMs;

    private static String defaultMqttUuid;

    private static String defaultMqttBroker;

    private static String defaultMqttTopic;

    private static int defaultMqttQos;

    private static AbstractMqttClient defautMqttClient;

    private static Deserializer<String> defaultJsonDeserializer;

    private static Serializer<String> defaultJsonSerializer;

    private static Logger logger;

    {
        try (final InputStream inputStream = RaspberryApplication.class.getClassLoader().getResourceAsStream("configuration.properties")) {
            final Properties properties = new Properties();
            properties.load(inputStream);

            defaultWebServiceBaseUrl = properties.getProperty("fr.polytech.raspberry.ws.defaultBaseUrl");
            defaultWebServiceResourcePathUrl = properties.getProperty("fr.polytech.raspberry.ws.defaultResourcePathUrl");
            defaultWebServiceRefreshRateInMs = Integer.parseInt(properties.getProperty("fr.polytech.raspberry.ws.defaultRefreshRateInMs"));

            defaultMqttUuid = properties.getProperty("fr.polytech.raspberry.mqtt.defaultUuid");
            defaultMqttBroker = properties.getProperty("fr.polytech.raspberry.mqtt.defaultBroker");
            defaultMqttTopic = properties.getProperty("fr.polytech.raspberry.mqtt.defaultTopic");
            defaultMqttQos = Integer.parseInt(properties.getProperty("fr.polytech.raspberry.mqtt.defaultQos"));

            defautMqttClient = new PahoMqttClient(defaultMqttUuid, defaultMqttBroker);
            defaultJsonDeserializer = new JsonDeserializer();
            defaultJsonSerializer = new JsonSerializer();

            logger = Logger.getLogger(RaspberryApplication.class.getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run() {
        final RestfulRequester restfulRequester = new StringRestfulRequester(defaultWebServiceBaseUrl);
        while (true) {
            try {
                // Get information from web-service
                final String webServiceResponse = restfulRequester.get(defaultWebServiceResourcePathUrl, String.class);
                final Energy energy = defaultJsonDeserializer.from(webServiceResponse, Energy.class);
                energy.setUserId(defaultMqttUuid);

                // Send information to server through an MQTT message
                defautMqttClient.publish(defaultMqttTopic, defaultJsonSerializer.to(energy), defaultMqttQos);
            } catch (Exception exception) {
                final Writer writer = new StringWriter();
                exception.printStackTrace(new PrintWriter(writer));

                logger.severe(writer.toString());
            }

            try {
                // Wait a bit
                Thread.sleep(defaultWebServiceRefreshRateInMs);
            } catch (Exception exception) {
                final Writer writer = new StringWriter();
                exception.printStackTrace(new PrintWriter(writer));

                logger.severe(writer.toString());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        final Thread applicationThread = new Thread(new RaspberryApplication());
        applicationThread.setDaemon(false);
        applicationThread.start();
    }
}