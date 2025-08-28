package ru.otus.kafkaStreams;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.SessionWindows;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.kstream.Grouped;
import ru.otus.kafkaStreams.model.EventMessage;
import ru.otus.kafkaStreams.utils.Utils;

import java.time.Duration;

import static ru.otus.kafkaStreams.utils.Utils.TOPIC_EVENTS;

@Slf4j
public class Main {
    public static void main(String[] args) {
        Utils.createTopic(TOPIC_EVENTS, 1, 1);

        var builder = new StreamsBuilder();
        Serde<String> stringSerde = Serdes.String();
        Serde<EventMessage> eventMessageSerde = Utils.eventMessageSerde();

        var fiveMinutesDuration = Duration.ofMinutes(5);

        KTable<Windowed<String>, Long> eventCounts = builder
            .stream(TOPIC_EVENTS, Consumed.with(stringSerde, eventMessageSerde))
            .groupBy((key, event) -> event.getCode(), Grouped.with(stringSerde, eventMessageSerde))
            .windowedBy(SessionWindows.ofInactivityGapWithNoGrace(fiveMinutesDuration))
            .count();

        eventCounts.toStream()
            .filter((key, count) -> count != null)
            .foreach((windowedKey, count) ->
                log.info("key {}: , count = {}", windowedKey.key(), count));

        final KafkaStreams streams = new KafkaStreams(builder.build(), Utils.createStreamsConfig());

        try {
            streams.start();
            log.info("Kafka Streams application started");
        } catch (Throwable e) {
            log.error("Error starting application", e);
        }
    }
}
