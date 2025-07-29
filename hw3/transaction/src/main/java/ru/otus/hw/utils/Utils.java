package ru.otus.hw.utils;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Utils {

    public static final String HOST_PRODUCER = "localhost:9091";
    public static final String HOST_CONSUMER = "localhost:9092";

    public static final Map<String, Object> producerConfig = Map.of(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, HOST_PRODUCER,
            ProducerConfig.ACKS_CONFIG, "all",
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class
    );

    public static final Map<String, Object> consumerConfig = Map.of(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, HOST_CONSUMER,
            ConsumerConfig.GROUP_ID_CONFIG, "consumer_group_id",
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class
    );

    public static final Map<String, Object> adminConfig = Map.of(
            AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, HOST_CONSUMER);

    public static Map<String, Object> createProducerConfig(Consumer<Map<String, Object>> builder) {
        var map = new HashMap<>(producerConfig);
        builder.accept(map);
        return map;
    }

    public static Map<String, Object> createConsumerConfig(Consumer<Map<String, Object>> builder) {
        var map = new HashMap<>(consumerConfig);
        builder.accept(map);
        return map;
    }
}