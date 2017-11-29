package fr.polytech.server.mqtt;

import fr.polytech.server.deserializers.Deserializer;
import fr.polytech.server.entities.Energy;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

import java.util.concurrent.TimeUnit;

public class InfluxDBMqttMessageHandler implements MqttMessageHandler {

    private static final String databaseName = "energyTimeSeries";

    private static final String retentionPolicy = "aRetentionPolicy";

    private static final int flushEveryXPoints = 2000;

    private static final int flushEveryXMs = 100;

    private final InfluxDB influxDB;

    private final Deserializer<String> deserializer;

    public InfluxDBMqttMessageHandler(Deserializer<String> deserializer, String influxDBHost, String influxDBUsername, String influxDBPassword) {
        this.deserializer = deserializer;
        this.influxDB = InfluxDBFactory.connect(influxDBHost, influxDBUsername, influxDBPassword);

        this.influxDB.createDatabase(databaseName);
        this.influxDB.setDatabase(databaseName);
        this.influxDB.setRetentionPolicy(retentionPolicy);
        this.influxDB.enableBatch(flushEveryXPoints, flushEveryXMs, TimeUnit.MILLISECONDS);
    }

    @Override
    public void handleMqttMessage(final String message) {
        final Energy energy = this.deserializer.from(message, Energy.class);
        this.influxDB.write(Point.measurement("energy").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS).addField("userId", energy.getUserId()).addField("product", energy.getProduct()).addField("t1Ptec", energy.getT1Ptec()).addField("t1Papp", energy.getT1Papp()).addField("t1Base", energy.getT1Base()).addField("t2Ptec", energy.getT2Ptec()).addField("t2Papp", energy.getT2Papp()).addField("t2Base", energy.getT2Base()).addField("indexC1", energy.getIndexC1()).addField("indexC2", energy.getIndexC2()).build());
    }
}