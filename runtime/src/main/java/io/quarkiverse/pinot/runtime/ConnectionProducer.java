package io.quarkiverse.pinot.runtime;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.pinot.client.Connection;
import org.apache.pinot.client.ConnectionFactory;

@Singleton
public class ConnectionProducer {
    @Inject
    PinotConfig pinotConfig;

    @Produces
    @Default
    @Singleton
    public Connection produce() {
        if (pinotConfig.zkUrl.isEmpty() && pinotConfig.hostList.isEmpty()) {
            throw new IllegalArgumentException("No Apache Pinot configuration found, " +
                    "you need to configure quarkus.pinot.zk-url or quarkus.pinot.host-list.");
        }
        if (pinotConfig.zkUrl.isPresent() && pinotConfig.hostList.isPresent()) {
            throw new IllegalArgumentException("Invalid Apache Pinot configuration found, " +
                    "you need to configure quarkus.pinot.zk-url or quarkus.pinot.host-list not both.");
        }

        if (pinotConfig.zkUrl.isPresent()) {
            return ConnectionFactory.fromZookeeper(pinotConfig.zkUrl.get());
        }

        return ConnectionFactory.fromHostList(pinotConfig.hostList.get().split(","));
    }
}
