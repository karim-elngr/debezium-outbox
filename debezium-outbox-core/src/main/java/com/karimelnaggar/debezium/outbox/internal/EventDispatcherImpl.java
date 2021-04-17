/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package com.karimelnaggar.debezium.outbox.internal;

import com.karimelnaggar.debezium.outbox.EventDispatcher;
import com.karimelnaggar.debezium.outbox.ExportedEvent;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.HashMap;

/**
 * An application-scope component that is responsible for observing
 * {@link ExportedEvent} events and when detected, persists those events to the
 * underlying database allowing Debezium to then capture and emit those change
 * events.
 *
 * @author Chris Cranford
 */
public class EventDispatcherImpl implements EventDispatcher {

    private static final String TIMESTAMP = "timestamp";
    private static final String PAYLOAD = "payload";
    private static final String TYPE = "type";
    private static final String AGGREGATE_ID = "aggregateId";
    private static final String AGGREGATE_TYPE = "aggregateType";

    private static final Logger LOGGER = LoggerFactory.getLogger(EventDispatcherImpl.class);

    private final EntityManager entityManager;

    /**
     * Debezium runtime configuration
     */
    private final DebeziumOutboxRuntimeConfig config;

    public EventDispatcherImpl(EntityManager entityManager, DebeziumOutboxRuntimeConfig config) {
        this.entityManager = entityManager;
        this.config = config;
    }

    /**
     * An event handler for {@link ExportedEvent} events and will be called when
     * the event fires.
     *
     * @param event the exported event
     */
    @Override
    public void onExportedEvent(ExportedEvent<?, ?> event) {

        LOGGER.debug("An exported event was found for type {}", event.getType());

        // Define the entity map-mode object using property names and values
        final HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put(AGGREGATE_TYPE, event.getAggregateType());
        dataMap.put(AGGREGATE_ID, event.getAggregateId());
        dataMap.put(TYPE, event.getType());
        dataMap.put(PAYLOAD, event.getPayload());
        dataMap.put(TIMESTAMP, event.getTimestamp());

        // Unwrap to Hibernate session and save
        Session session = entityManager.unwrap(Session.class);
        session.save(OutboxConstants.OUTBOX_ENTITY_FULLNAME, dataMap);
        session.setReadOnly(dataMap, true);

        // Remove entity if the configuration deems doing so, leaving useful
        // for debugging
        if (config.isRemoveAfterInsert()) {
            session.delete(OutboxConstants.OUTBOX_ENTITY_FULLNAME, dataMap);
        }
    }
}
