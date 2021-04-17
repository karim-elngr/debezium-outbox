/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package com.karimelnaggar.debezium.outbox.starter.internal;

import com.karimelnaggar.debezium.outbox.starter.EventDispatcher;
import com.karimelnaggar.debezium.outbox.starter.ExportedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An application-scope component that is responsible for observing
 * {@link ExportedEvent} events and when detected, persists those events to the
 * underlying database allowing Debezium to then capture and emit those change
 * events.
 *
 * @author Chris Cranford
 */
public class EventDispatcherImpl implements EventDispatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventDispatcherImpl.class);

    private final OutboxEventPersistence outboxEventPersistence;

    /**
     * Debezium runtime configuration
     */
    private final DebeziumOutboxRuntimeConfig config;

    public EventDispatcherImpl(OutboxEventPersistence outboxEventPersistence, DebeziumOutboxRuntimeConfig config) {
        this.outboxEventPersistence = outboxEventPersistence;
        this.config = config;
    }

    /**
     * An event handler for {@link ExportedEvent} events and will be called when
     * the event fires.
     *
     * @param event the exported event
     */
    @Override
    public void onExportedEvent(ExportedEvent event) {
        LOGGER.debug("An exported event was found for type {}", event.getType());

        final OutboxEvent outboxEvent = new OutboxEvent(
                event.getAggregateType(),
                event.getAggregateId(),
                event.getType(),
                event.getTimestamp(),
                event.getPayload()
        );

        // Unwrap to Hibernate session and save
        final OutboxEvent savedOutboxEvent = outboxEventPersistence.save(outboxEvent);

        // Remove entity if the configuration deems doing so, leaving useful
        // for debugging
        if (config.isRemoveAfterInsert()) {
            outboxEventPersistence.delete(savedOutboxEvent);
        }
    }
}
