package ru.otus.kafkaStreams.serde;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import ru.otus.kafkaStreams.model.EventMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class EventSerializer implements Serializer<EventMessage> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, EventMessage message) {
        try {
            if (message == null) {
                return null;
            }
            return objectMapper.writeValueAsBytes(message);
        } catch (Exception e) {
            throw new SerializationException("Error serializing");
        }
    }

    @Override
    public void close() {
    }
}
