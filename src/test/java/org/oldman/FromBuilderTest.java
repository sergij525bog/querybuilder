package org.oldman;

import org.junit.jupiter.api.Test;
import org.oldman.models.TableInfo;

import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FromBuilderTest {
    private static final QueryGraph QUERY_GRAPH = new QueryGraph(new EnumMap<>(Clauses.class));

    @Test
    public void itShouldFailIfParamIsNull() {
        FromBuilder builder = new FromBuilder(QUERY_GRAPH);

        assertThrows(IllegalArgumentException.class, () -> builder.from(null));
    }

    @Test
    public void itShouldSaveFromSuccessfully() {
        FromBuilder builder = new FromBuilder(QUERY_GRAPH);
        TableInfo fromTable = new TableInfo("Table", "t");

        builder.from(fromTable);
        List<String> fromPart = builder.buildQueryPart().collect(Collectors.toList());

        assertEquals(Clauses.FROM.getClause(), fromPart.get(0));
        assertEquals(fromTable.getInfo(), fromPart.get(1));
    }
}
