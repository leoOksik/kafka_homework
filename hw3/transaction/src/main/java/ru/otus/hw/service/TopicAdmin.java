package ru.otus.hw.service;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw.utils.Utils;

import java.util.List;
import java.util.stream.Collectors;

public class TopicAdmin {
    private static final Logger log = LoggerFactory.getLogger(TopicAdmin.class);

    public static void createTopics(List<String> topics, int partitions, int replicationFactor) {

        try (AdminClient adminClient = AdminClient.create(Utils.adminConfig)) {

            List<NewTopic> topicList = topics.stream()
                    .map(topic -> new NewTopic(topic, partitions, (short) replicationFactor))
                    .collect(Collectors.toList());

            adminClient.createTopics(topicList);

            log.info("Topics created successfully: {}", topicList);

        } catch (Exception e) {
            log.error("Error creating: {}", e.getMessage());
        }
    }
}
