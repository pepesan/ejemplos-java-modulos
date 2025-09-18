package com.cursosdedesarrollo;


import org.junit.jupiter.api.Test;

import static java.lang.ScopedValue.where;
import java.lang.ScopedValue;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Joiner;

public class AppTest {

    // Mejora de Java 25
    // Declara el ScopedValue como campo estático de clase
    private static final ScopedValue<String> REQUEST_ID =
            ScopedValue.newInstance();

    @Test
    void testScoped() {

        try (var scope = StructuredTaskScope
                .open(Joiner.allSuccessfulOrThrow())) {
            scope.fork(() -> where(REQUEST_ID, "abc-123")
                    .run(AppTest::handleRequest));
            scope.fork(() -> where(REQUEST_ID, "xyz-789")
                    .run(AppTest::handleRequest));

            scope.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    static void handleRequest() {
        log("handleRequest: start");

        try (var scope = StructuredTaskScope
                .open(Joiner.allSuccessfulOrThrow())) {
            scope.fork(AppTest::queryDatabase);
            scope.fork(AppTest::callExternalService);
            scope.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log("handleRequest: done");
    }

    static void queryDatabase() {
        log("Querying database...");
    }

    static void callExternalService() {
        log("Calling external service...");
    }

    static void log(String message) {
        String rid = REQUEST_ID.isBound() ?
                REQUEST_ID.get() : "<unbound>";
        System.out.println("[" + rid + "] " + message);
    }
}
