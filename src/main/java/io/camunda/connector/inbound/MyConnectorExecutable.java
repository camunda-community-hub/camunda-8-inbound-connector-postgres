package io.camunda.connector.inbound;

import io.camunda.connector.api.annotation.InboundConnector;
import io.camunda.connector.api.inbound.InboundConnectorContext;
import io.camunda.connector.api.inbound.InboundConnectorExecutable;
import io.camunda.connector.inbound.subscription.PostgresChannelSubscription;
import io.camunda.connector.inbound.subscription.PostgresChannelSubscriptionEvent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@InboundConnector(name = "POSTGRESINBOUNDCONNECTOR", type = "io.camunda:postgresinbound:1")
public class MyConnectorExecutable implements InboundConnectorExecutable {

  private PostgresChannelSubscription subscription;
  private InboundConnectorContext connectorContext;
  private ExecutorService executorService;
  public CompletableFuture<?> future;


  @Override
  public void activate(InboundConnectorContext connectorContext) {
    MyConnectorProperties props = connectorContext.bindProperties(MyConnectorProperties.class);
    this.connectorContext = connectorContext;
    this.executorService = Executors.newSingleThreadExecutor();

    this.future =
            CompletableFuture.runAsync(
                    () -> {
                      new PostgresChannelSubscription(props.getPostgresUrl(), props.getPostgresUsername(), props.getPostgresPassword(), props.getChannel(), props.getPollingInterval(), this::onEvent);
                    },
                     this.executorService);

  }

  @Override
  public void deactivate() {
    subscription.stop();
  }

  private void onEvent(PostgresChannelSubscriptionEvent rawEvent) {
    MyConnectorEvent connectorEvent = new MyConnectorEvent(rawEvent);
    connectorContext.correlate(connectorEvent);
  }
}
