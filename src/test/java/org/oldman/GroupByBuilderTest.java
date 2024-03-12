package org.oldman;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.oldman.enums.HqlFunction;
import org.oldman.enums.Operator;
import org.oldman.models.FieldInfo;

import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GroupByBuilderTest {
    private static QueryGraph queryGraph;

    @BeforeEach
    public void setup() {
        queryGraph = new QueryGraph(new EnumMap<>(Clauses.class));
    }

    @Test
    public void itShouldSaveGroupByPart() {
        GroupByBuilder builder = new GroupByBuilder(queryGraph);
        List<FieldInfo> fields = List.of(
                FieldInfo.withAlias("t.field", "f"),
                FieldInfo.withAlias("t.field2", "f2"));
        String expected = "group by t.field, t.field2";

        builder.groupBy(fields);

        assertEquals(expected, builder.buildQueryPart().collect(Collectors.joining(" ")));
    }

    @Test
    public void itShouldSaveGroupByPartWithHaving() {
        GroupByBuilder builder = new GroupByBuilder(queryGraph);
        List<FieldInfo> fields = List.of(
                FieldInfo.withAlias("t.field", "f"),
                FieldInfo.withAlias("t.field2", "f2"));
        String expected = "group by t.field, t.field2 " +
                "having sum(t.field) > 100 " +
                "and avg(t.field2) < 10 " +
                "or avg(t.field2) >= 40";

        builder.groupBy(fields)
                .having(HqlFunction.SUM, fields.get(0), Operator.MORE, "100")
                .and(HqlFunction.AVG, fields.get(1), Operator.LESS, "10")
                .or(HqlFunction.AVG, fields.get(1), Operator.MORE_OR_EQUAL, "40");

        assertEquals(expected, builder.buildQueryPart().collect(Collectors.joining(" ")));
    }
}
