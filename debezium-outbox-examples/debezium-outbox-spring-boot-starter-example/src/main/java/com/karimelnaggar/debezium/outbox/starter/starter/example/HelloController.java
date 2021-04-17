package com.karimelnaggar.debezium.outbox.starter.starter.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.karimelnaggar.debezium.outbox.starter.EventDispatcher;
import com.karimelnaggar.debezium.outbox.starter.ExportedEvent;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class HelloController {

    private final EventDispatcher eventDispatcher;

    public HelloController(EventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
    }

    @RequestMapping("/")
    public String index() {

        eventDispatcher.onExportedEvent(new ExportedEvent() {
            @Override
            public String getAggregateId() {
                return "AggregateId";
            }

            @Override
            public String getAggregateType() {
                return "AggregateType";
            }

            @Override
            public String getType() {
                return "SomeType";
            }

            @Override
            public Instant getTimestamp() {
                return Instant.now();
            }

            @Override
            public JsonNode getPayload() {

                final ObjectMapper objectMapper = new ObjectMapper();

                final ObjectNode payload = objectMapper.createObjectNode();

                payload.put("transaction-id", "id");

                return payload;
            }
        });

        return "Greetings from Spring Boot!";
    }
}
