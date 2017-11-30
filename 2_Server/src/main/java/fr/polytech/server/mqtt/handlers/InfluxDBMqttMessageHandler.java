package fr.polytech.server.mqtt.handlers;

import fr.polytech.server.deserializers.Deserializer;
import fr.polytech.server.entities.EnergyReport;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class InfluxDBMqttMessageHandler implements MqttMessageHandler {

    private static final Logger logger = Logger.getLogger(InfluxDBMqttMessageHandler.class.getSimpleName());

    private final InfluxDB influxDB;

    private final Deserializer<String> deserializer;

    public InfluxDBMqttMessageHandler(String influxDBHost, String influxDBUsername, String influxDBPassword, String influxDBDatabaseName, String influxDBRetentionPolicy, Deserializer<String> deserializer) {
        this.influxDB = InfluxDBFactory.connect(influxDBHost, influxDBUsername, influxDBPassword);
        this.influxDB.createDatabase(influxDBDatabaseName);
        this.influxDB.setDatabase(influxDBDatabaseName);
        this.influxDB.setRetentionPolicy(influxDBRetentionPolicy);
        this.deserializer = deserializer;
    }

    @Override
    public void handleMqttMessage(final String message) {
        logger.info("mqqtMessageContent: " + message);

        final EnergyReport energyReport = this.deserializer.from(message, EnergyReport.class);
        final Point measure = Point.measurement("energy") //
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS) //
                .addField("userId", energyReport.getUserId()) //
                .addField("product", energyReport.getProduct()) //
                .addField("t1Ptec", energyReport.getT1Ptec()) //
                .addField("t1Papp", energyReport.getT1Papp()) //
                .addField("t1Base", energyReport.getT1Base()) //
                .addField("t2Ptec", energyReport.getT2Ptec()) //
                .addField("t2Papp", energyReport.getT2Papp()) //
                .addField("t2Base", energyReport.getT2Base()) //
                .addField("indexC1", energyReport.getIndexC1()) //
                .addField("indexC2", energyReport.getIndexC2()) //
                .build();

        this.influxDB.write(measure);
    }
}