package com.karimelnaggar.debezium.outbox.starter.internal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OutboxEventPersistence extends JpaRepository<OutboxEvent, UUID> {
}
