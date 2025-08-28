package ru.otus.kafkaStreams.serde;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import ru.otus.kafkaStreams.model.EventMessage;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class EventDeserializer implements Deserializer<EventMessage> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public EventMessage deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                return null;
            }
            return objectMapper.readValue(new String(data, StandardCharsets.UTF_8), EventMessage.class);
        } catch (Exception e) {
            throw new SerializationException("Error deserializing");
        }
    }

    @Override
    public void close() {
    }
}
