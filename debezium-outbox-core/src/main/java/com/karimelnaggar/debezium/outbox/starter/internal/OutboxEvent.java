package com.karimelnaggar.debezium.outbox.starter.internal;

import com.fasterxml.jackson.databind.JsonNode;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Immutable
@Table(name = "outbox_events")
public class OutboxEvent {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String aggregateType;

    @Column(nullable = false)
    private String aggregateId;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Instant timestamp;

    @Convert(converter = JsonNodeAttributeConverter.class)
    @Column(nullable = false, columnDefinition = "varchar(8000)")
    private JsonNode payload;

    @Column(length = 256)
    private String tracingSpanContext;

    public OutboxEvent() {
    }

    public OutboxEvent(String aggregateType, String aggregateId, String type, Instant timestamp, JsonNode payload) {
        this(null, aggregateType, aggregateId, type, timestamp, payload, null);
    }

    public OutboxEvent(UUID id, String aggregateType, String aggregateId, String type, Instant timestamp, JsonNode payload, String tracingSpanContext) {
        this.id = id;
        this.aggregateType = aggregateType;
        this.aggregateId = aggregateId;
        this.type = type;
        this.timestamp = timestamp;
        this.payload = payload;
        this.tracingSpanContext = tracingSpanContext;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAggregateType() {
        return aggregateType;
    }

    public void setAggregateType(String aggregateType) {
        this.aggregateType = aggregateType;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public void setAggregateId(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public JsonNode getPayload() {
        return payload;
    }

    public void setPayload(JsonNode payload) {
        this.payload = payload;
    }

    public String getTracingSpanContext() {
        return tracingSpanContext;
    }

    public void setTracingSpanContext(String tracingSpanContext) {
        this.tracingSpanContext = tracingSpanContext;
    }
}
