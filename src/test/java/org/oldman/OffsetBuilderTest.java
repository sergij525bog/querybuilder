package org.oldman;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OffsetBuilderTest {
    private static QueryGraph queryGraph;

    @BeforeEach
    public void setup() {
        queryGraph = new QueryGraph(new EnumMap<>(Clauses.class));
    }

    @Test
    public void itShouldSaveOffsetPart() {
        OffsetBuilder builder = new OffsetBuilder(queryGraph);
        String expected = "offset 10";

        builder.offset(10);

        assertEquals(expected, builder.buildQueryPart().collect(Collectors.joining(" ")));
    }

    @Test
    public void itShouldBuildQuery() {
        OffsetBuilder builder = new OffsetBuilder(queryGraph);
        String expected = "offset 10";

        builder.offset(10);

        assertEquals(expected, builder.buildQueryString());
    }
}
