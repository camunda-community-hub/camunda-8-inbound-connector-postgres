package io.camunda.connector.inbound;

import io.camunda.connector.inbound.subscription.PostgresChannelSubscriptionEvent;

import java.util.Objects;

/**
 * Data model of an event produced by the inbound Connector
 */
public class MyConnectorEvent {
  private final PostgresChannelSubscriptionEvent event;

  public MyConnectorEvent(PostgresChannelSubscriptionEvent event) {
    this.event = event;
  }

  public PostgresChannelSubscriptionEvent getEvent() {
    return event;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MyConnectorEvent that = (MyConnectorEvent) o;
    return Objects.equals(event, that.event);
  }

  @Override
  public int hashCode() {
    return Objects.hash(event);
  }

  @Override
  public String toString() {
    return "MyConnectorEvent{" +
        "event=" + event +
        '}';
  }
}
