package ru.otus.kafkaStreams.utils;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import ru.otus.kafkaStreams.model.EventMessage;
import ru.otus.kafkaStreams.serde.EventDeserializer;
import ru.otus.kafkaStreams.serde.EventSerializer;

public class Utils {

    public static final String HOST = "localhost:9092";
    public static final String TOPIC_EVENTS = "events";

    public static final Map<String, Object> adminConfig = Map.of(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, HOST);

    public static void doAdminAction(AdminClientConsumer action) {
        try (var client = Admin.create(Utils.adminConfig)) {
            action.accept(client);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public interface AdminClientConsumer {
        void accept(Admin client) throws Exception;
    }

    public static void createTopic( String topicName, int numPartitions, int replicationFactor) {
        doAdminAction(admin -> admin.createTopics(
            Collections.singletonList(new NewTopic(topicName, numPartitions, (short) replicationFactor))));
    }

    public static Serde<EventMessage> eventMessageSerde() {
        return Serdes.serdeFrom(new EventSerializer(), new EventDeserializer());
    }

    public static final Map<String, Object> kafkaStreamsConfig = Map.of(
        StreamsConfig.APPLICATION_ID_CONFIG, "event-counter",
        StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, HOST
    );

    public static Properties createStreamsConfig() {
        Properties props = new Properties();
        props.putAll(kafkaStreamsConfig);
        return props;
    }
}
