package ru.otus.hw.service;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomCallback implements Callback {
    private static final Logger log = LoggerFactory.getLogger(CustomCallback.class);

    @Override
    public void onCompletion(RecordMetadata metadata, Exception exception) {
        if (exception != null) {
            log.error("Send failed: {} {}", exception.getClass().getSimpleName(), exception.getMessage(), exception);
        } else {
            log.info("Message sent -> topic: {}, partition: {}, offset: {}",
                    metadata.topic(), metadata.partition(), metadata.offset());
        }
    }
}
