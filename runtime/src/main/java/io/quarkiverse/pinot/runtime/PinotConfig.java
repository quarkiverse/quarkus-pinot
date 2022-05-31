package io.quarkiverse.pinot.runtime;

import java.util.Optional;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

@ConfigRoot(phase = ConfigPhase.RUN_TIME)
public class PinotConfig {
    /**
     * Zookeeper URL in the format {host1}:{port1}/{path1},{host2}:{port2}/{path2}.
     * 
     * @see org.apache.pinot.client.ConnectionFactory#fromZookeeper(String).
     */
    @ConfigItem
    public Optional<String> zkUrl;

    /**
     * Host list in the format {broker1}:{port1},{broker2:port2}.
     * 
     * @see org.apache.pinot.client.ConnectionFactory#fromHostList(String...) .
     */
    @ConfigItem
    public Optional<String> hostList;
}
