[![Community Extension](https://img.shields.io/badge/Community%20Extension-An%20open%20source%20community%20maintained%20project-FF4700)](https://github.com/camunda-community-hub/community)
[![](https://img.shields.io/badge/Lifecycle-Proof%20of%20Concept-blueviolet)](https://github.com/Camunda-Community-Hub/community/blob/main/extension-lifecycle.md#proof-of-concept-)
![Compatible with: Camunda Platform 8](https://img.shields.io/badge/Compatible%20with-Camunda%20Platform%208-0072Ce)

# Inbound Postgres Connector Template

!!! Work in progress !!!

A starting point to build a Start and Intermediate Inbound Postgres Connector. Use the Notify/Listen feature along with database triggers in Postgres to start process instances or catch intermediate events. Feedback and PRs are welcome! In the Connector you'll need to provide the Postgres URL, username, password, channel, the directory to be monitored, and the long polling interval in seconds.

There is one output. It can be accessed from the ```event``` object:
```event.payload```

The payload is defined in the trigger function and is limited to a string. JSON formatted trigger output text will be treated as JSON in the output.

![Example](./img/sample.png)

An example of JSON formatted text in a trigger notifying a channel in Postgres:

![Postgres example](./img/pgsample.png)

For intermediate catch events you need to set a correlation key in the process using a variable and send the correlation key in the payload as part of the incoming message

## Build

You can package the Connector by running the following command:

```bash
mvn clean package
```

This will create the following artifacts:

- A thin JAR without dependencies.
- An uber JAR containing all dependencies, potentially shaded to avoid classpath conflicts. This will not include the SDK artifacts since those are in scope `provided` and will be brought along by the respective Connector Runtime executing the Connector.

### Shading dependencies

You can use the `maven-shade-plugin` defined in the [Maven configuration](./pom.xml) to relocate common dependencies
that are used in other Connectors and the [Connector Runtime](https://github.com/camunda-community-hub/spring-zeebe/tree/master/connector-runtime#building-connector-runtime-bundles).
This helps to avoid classpath conflicts when the Connector is executed.

Use the `relocations` configuration in the Maven Shade plugin to define the dependencies that should be shaded.
The [Maven Shade documentation](https://maven.apache.org/plugins/maven-shade-plugin/examples/class-relocation.html)
provides more details on relocations.

## API

### Connector Properties

This Connector can be configured with the following properties:

| Name                                                     | Description                                    | Example                                     |
|----------------------------------------------------------|------------------------------------------------|---------------------------------------------|
| Postgres URL                                             | URL of Postgres database                       | `jdbc:postgresql://localhost:5432/postgres` |
| Username                                                 | Postgres username to authenticate with         | `postgres`                                  |
| Password                                                 | Password to authenticate with                  | `postgres`                                  |
| Channel                                                  | Postgres channel to listen for events          | `inboundchannel`                            |
| Polling interval                                         | Long polling interval in seconds               | `60`                                        |
| Correlation key (process) - for intermediate catch event | Process variable to correlate incoming message | `processCorrelationKey`                     |
| Correlation key (payload) - for intermediate catch event | Correlation key in incoming message            | `event.payload.messageCorrelationKey`       |
| Activation condition      - for intermediate catch event | Condition under which the Connector triggers   | `event.payload.aVariable = 'ok'`            |

### Output

This Connector produces the following output:

```json
{
  "event": {
    "payload": "whatever is set in the Postgres trigger, ideally well formed JSON"
  }
}
```

## Test locally

Run unit tests

```bash
mvn clean verify
```

### Test with local runtime

Use the [Camunda Connector Runtime](https://github.com/camunda-community-hub/spring-zeebe/tree/master/connector-runtime#building-connector-runtime-bundles) to run your function as a local Java application.

In your IDE you can also simply navigate to the `LocalContainerRuntime` class in test scope and run it via your IDE.
If necessary, you can adjust `application.properties` in test scope.

## Element Template

The element templates can be found in the element-templates directory.
