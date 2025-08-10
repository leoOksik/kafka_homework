package ru.otus.hw.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.List;

public class ProducerService {
    public static void sendMessages(List<String> topics, int count, KafkaProducer<String, String> producer, boolean isCommitTransaction) throws InterruptedException {
        for (int i = 0; i < count; i++) {
            producer.beginTransaction();
            for (String topic : topics) {
                producer.send(new ProducerRecord<>(topic, topic + " -> " + i), new CustomCallback());
            }
            Thread.sleep(500);
            Runnable runnable = isCommitTransaction ? producer::commitTransaction : producer::abortTransaction;
            runnable.run();
        }
    }
}
