package com.karimelnaggar.debezium.outbox.starter;

public interface EventDispatcher {

    /**
     * An event handler for {@link ExportedEvent} events and will be called when
     * the event fires.
     *
     * @param event the exported event
     */
    void onExportedEvent(ExportedEvent event);
}
