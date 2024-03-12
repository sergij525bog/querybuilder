package org.oldman;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LimitBuilderTest {
    private static QueryGraph queryGraph;

    @BeforeEach
    public void setup() {
        queryGraph = new QueryGraph(new EnumMap<>(Clauses.class));
    }
    @Test
    public void isShouldSaveLimitPart() {
        LimitBuilder builder = new LimitBuilder(queryGraph);
        String expected = "limit 5";

        builder.limit(5);

        assertEquals(expected, builder.buildQueryPart().collect(Collectors.joining(" ")));
    }
}
