package io.camunda.connector.inbound.subscription;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

/**
 * Data model of an event consumed by inbound Connector (e.g. originating from an external system)
 */
public class PostgresChannelSubscriptionEvent {
  private final Object payload;

  public PostgresChannelSubscriptionEvent(String payload) throws JsonProcessingException {
      ObjectMapper mapper = new ObjectMapper();
      this.payload = mapper.readTree(payload);
  }

  public Object getPayload() {
    return payload;
  }

    @Override
  public boolean equals(Object o) {
    System.out.println("checking...");
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostgresChannelSubscriptionEvent that = (PostgresChannelSubscriptionEvent) o;
    return Objects.equals(payload, that.payload);
  }

  @Override
  public String toString() {
    return "PostgresChannelSubscriptionEvent{" +
            "payload='" + payload + '\'' +
        '}';
  }
}
