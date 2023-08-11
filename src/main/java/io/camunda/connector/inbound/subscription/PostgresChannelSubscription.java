package io.camunda.connector.inbound.subscription;

import org.postgresql.PGConnection;
import org.postgresql.PGNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;
import java.util.function.Consumer;

import java.sql.Connection;
import java.sql.DriverManager;

public class PostgresChannelSubscription {

    private final static Logger LOG = LoggerFactory.getLogger(PostgresChannelSubscription.class);

    public PostgresChannelSubscription(String postgresUrl, String postgresUsername, String postgresPassword, String channel, String pollingInterval, Consumer<PostgresChannelSubscriptionEvent> callback) {
        LOG.info("Activating Postgres Channel subscription");
        // listen to channel in Postgres
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(postgresUrl, postgresUsername, postgresPassword);
            Statement stmt = c.createStatement();
            String sql = "LISTEN "+channel;
            stmt.execute(sql);
            stmt.close();

            PGConnection pgconn = c.unwrap(PGConnection.class);

            while(true) {
                PGNotification notifications[] = pgconn.getNotifications(Integer.parseInt(pollingInterval)*1000);

                if (notifications.length > 0) {
                    for (int i=0; i < notifications.length; i++) {
                        LOG.info("Got notification: " + notifications[i].getParameter());
                        PostgresChannelSubscriptionEvent pgse = new PostgresChannelSubscriptionEvent(notifications[i].getParameter());
                        callback.accept(pgse);
                    }
                } else {
                    LOG.info("No notifications");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

    }

    public void stop() {
        LOG.info("Deactivating Postgres inbound service");
    }


}
