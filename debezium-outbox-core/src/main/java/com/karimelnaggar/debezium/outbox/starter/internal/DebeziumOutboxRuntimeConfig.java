package com.karimelnaggar.debezium.outbox.starter.internal;

public final class DebeziumOutboxRuntimeConfig {

    private boolean removeAfterInsert = true;

    public DebeziumOutboxRuntimeConfig() {
    }

    public boolean isRemoveAfterInsert() {
        return removeAfterInsert;
    }

    public void setRemoveAfterInsert(boolean removeAfterInsert) {
        this.removeAfterInsert = removeAfterInsert;
    }
}
