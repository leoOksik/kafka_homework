package ru.otus.hw;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw.service.CustomService;
import ru.otus.hw.service.ProducerService;
import ru.otus.hw.service.TopicAdmin;
import ru.otus.hw.utils.Utils;

import java.util.List;

public class Transaction {
    private static final Logger log = LoggerFactory.getLogger(Transaction.class);

    public static void main(String[] args) throws Exception {

        List<String> topicList = List.of("topic1", "topic2");
        TopicAdmin.createTopics(topicList, 1, 1);

        try (var producer = new KafkaProducer<String, String>(Utils.createProducerConfig(p ->
                p.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "transaction")));
             var consumer = new KafkaConsumer<String, String>(Utils.createConsumerConfig(p ->
                     p.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed")))) {

            producer.initTransactions();

            log.info("Committed transaction (producer)");

            ProducerService.sendMessages(topicList, 5, producer, true);
            Thread.sleep(1000);

           log.info("Aborted transaction (producer)");

            ProducerService.sendMessages(topicList, 2, producer, false);
            Thread.sleep(1000);

           log.info("Read committed transaction (consumer)");

            CustomService.readMessages(topicList, consumer);

        }
    }
}
