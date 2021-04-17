package com.karimelnaggar.debezium.outbox.internal;

public final class DebeziumOutboxRuntimeConfig {

    private final boolean removeAfterInsert;

    public DebeziumOutboxRuntimeConfig(boolean removeAfterInsert) {
        this.removeAfterInsert = removeAfterInsert;
    }

    public boolean isRemoveAfterInsert() {
        return removeAfterInsert;
    }
}
