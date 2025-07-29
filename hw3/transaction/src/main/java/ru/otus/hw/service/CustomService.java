package ru.otus.hw.service;

import java.time.Duration;
import java.util.List;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomService {
    private static final Logger log = LoggerFactory.getLogger(CustomService.class);

    public static void readMessages(List<String> topics, Consumer<String, String> consumer) {
        consumer.subscribe(topics);

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                log.info("topic = {}, key = {}, value = {}, offset = {} \n",
                        record.topic(), record.key(), record.value(), record.offset());
            }
        }
    }
}
