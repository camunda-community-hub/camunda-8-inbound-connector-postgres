package io.camunda.connector.inbound;

/**
 * Configuration properties for inbound Connector
 */
public class MyConnectorProperties {

  private String postgresUrl;
  private String postgresUsername;
  private String postgresPassword;
  private String channel;
  private String pollingInterval;


  public String getPostgresUrl() {
    return postgresUrl;
  }
  public String getPostgresUsername() {
    return postgresUsername;
  }
  public String getPostgresPassword() {
    return postgresPassword;
  }
  public String getChannel() {
    return channel;
  }
  public String getPollingInterval() {
    return pollingInterval;
  }


  public void setPostgresUrl(String postgresUrl) {
    this.postgresUrl = postgresUrl;
  }
  public void setPostgresUsername(String postgresUsername) {
    this.postgresUsername = postgresUsername;
  }
  public void setPostgresPassword(String postgresPassword) {
    this.postgresPassword = postgresPassword;
  }
  public void setChannel(String channel) {
    this.channel = channel;
  }
  public void setPollingInterval(String pollingInterval) {
    this.pollingInterval = pollingInterval;
  }

  @Override
  public String toString() {
    return "MyConnectorProperties{" +
        "postgres URL='" + postgresUrl + '\'' +
        "channel='" + channel + '\'' +
        "polling interval='" + pollingInterval + '\'' +
        '}';
  }
}
