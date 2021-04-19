package com.habeebcycle.sse.util.payload;

import java.util.Objects;

public class MessagePayload {
    private String id;
    private String author;
    private String message;
    private String timestamp;
    private String serviceAddress;

    public MessagePayload() {
    }

    public MessagePayload(String author, String message, String timestamp) {
        this.author = author;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessagePayload that = (MessagePayload) o;
        return Objects.equals(author, that.author) && Objects.equals(message, that.message) && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, message, timestamp);
    }

    @Override
    public String toString() {
        return "MessagePayload{" +
                "author='" + author + '\'' +
                ", message='" + message + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
