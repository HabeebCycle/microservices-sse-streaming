package com.habeebcycle.sse.util.events;

import java.time.LocalDateTime;

public class DataEvent<K, T> {

    private final K key;
    private final T data;
    private final EventType eventType;
    private final LocalDateTime eventCreatedAt;

    public DataEvent() {
        this.key = null;
        this.data = null;
        this.eventType = null;
        this.eventCreatedAt = null;
    }

    public DataEvent(EventType eventType, K key, T data) {
        this.key = key;
        this.data = data;
        this.eventType = eventType;
        this.eventCreatedAt = LocalDateTime.now();
    }

    public K getKey() {
        return key;
    }

    public T getData() {
        return data;
    }

    public EventType getEventType() {
        return eventType;
    }

    public LocalDateTime getEventCreatedAt() {
        return eventCreatedAt;
    }
}
