package org.oldman;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.oldman.models.FieldInfo;

import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderByBuilderTest {
    private static QueryGraph queryGraph;

    @BeforeEach
    public void setup() {
        queryGraph = new QueryGraph(new EnumMap<>(Clauses.class));
    }

    @Test
    public void itShouldSaveOrderByPart() {
        OrderByBuilder builder = new OrderByBuilder(queryGraph);
        List<FieldInfo> fields = List.of(
                FieldInfo.withAlias("t.field", "f"),
                FieldInfo.withAlias("t.field2", "f2")
        );
        String expected = "order by t.field, t.field2";

        builder.orderBy(fields);

        assertEquals(expected, builder.buildQueryPart().collect(Collectors.joining(" ")));
    }
}
