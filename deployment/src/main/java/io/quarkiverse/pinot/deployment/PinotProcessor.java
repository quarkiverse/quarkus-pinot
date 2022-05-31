package io.quarkiverse.pinot.deployment;

import io.quarkiverse.pinot.runtime.ConnectionProducer;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;

class PinotProcessor {

    private static final String FEATURE = "pinot";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    AdditionalBeanBuildItem producer() {
        return new AdditionalBeanBuildItem(ConnectionProducer.class);
    }
}
