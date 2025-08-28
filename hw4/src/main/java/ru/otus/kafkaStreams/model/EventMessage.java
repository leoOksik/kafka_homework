package ru.otus.kafkaStreams.model;

import lombok.Getter;

@Getter
public class EventMessage {
    String code;
    String message;
}
