package com.neo4j.docker.utils;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;

public class Neo4jAssertions {
    // An upcoming release bundles the incubator vector module by default, which makes the JVM emit
    // this warning to stderr on startup. It is expected, so we tolerate it (but nothing else).
    private static final String INCUBATOR_MODULE_WARNING = "WARNING: Using incubator modules: jdk.incubator.vector";

    private Neo4jAssertions() {}

    /**
     * Asserts that the given stderr output contains no errors. It must either be empty or contain
     * only the expected incubator module warning; any other non-blank line fails the assertion.
     */
    public static void assertNoUnexpectedErrors(String stderr) {
        String unexpected = Stream.of(stderr.split("\n"))
                .map(String::strip)
                .filter(line -> !line.isEmpty())
                .filter(line -> !line.equals(INCUBATOR_MODULE_WARNING))
                .collect(Collectors.joining("\n"));

        Assertions.assertEquals("", unexpected, "Unexpected errors in stderr from container!\n" + stderr);
    }
}
