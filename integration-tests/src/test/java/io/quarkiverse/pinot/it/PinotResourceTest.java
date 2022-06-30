package io.quarkiverse.pinot.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class PinotResourceTest {

    private static GenericContainer<?> pinotdContainer = new GenericContainer<>("apachepinot/pinot:0.9.3")
            .withCommand("QuickStart", "-type", "batch")
            .waitingFor(Wait.forLogMessage(".*Offline quickstart setup complete.*\\n", 1));

    @BeforeAll
    static void startContainer() {
        pinotdContainer.setPortBindings(List.of("2181:2123"));
        pinotdContainer.start();
    }

    @AfterAll
    static void stopContainer() {
        pinotdContainer.stop();
    }

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/pinot")
                .then()
                .statusCode(200)
                .body(is("Adrian | 1820.0\n" +
                        "Jose Antonio | 1692.0\n" +
                        "Rafael | 1565.0\n" +
                        "Brian Michael | 1500.0\n" +
                        "Jose Alberto | 1426.0\n" +
                        "Alexander Emmanuel | 1426.0\n" +
                        "Derek Sanderson | 1390.0\n" +
                        "Carlos | 1314.0\n" +
                        "Johnny David | 1300.0\n" +
                        "Ichiro | 1261.0\n"));
    }
}
