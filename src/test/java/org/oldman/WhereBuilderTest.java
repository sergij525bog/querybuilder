package org.oldman;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.oldman.enums.Operator;
import org.oldman.models.FieldInfo;

import java.util.EnumMap;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WhereBuilderTest {
    private static QueryGraph queryGraph;

    @BeforeEach
    public void setup() {
        queryGraph = new QueryGraph(new EnumMap<>(Clauses.class));
    }

    @Test
    public void itShouldFailIfFieldOrParameterIsNull() {
        WhereBuilder builder = new WhereBuilder(queryGraph);

        assertThrows(IllegalArgumentException.class, () -> builder.where(null, Operator.MORE, ""));
        assertThrows(
                IllegalArgumentException.class,
                () -> builder.where(
                        FieldInfo.withAlias("t.field", "f"),
                        Operator.MORE,
                        null)
        );
    }

    @Test
    public void itShouldSaveWherePart() {
        WhereBuilder builder = new WhereBuilder(queryGraph);
        FieldInfo field = FieldInfo.withAlias("t.field", "f");
        String expected = "where t.field > :param";

        builder.where(field, Operator.MORE, ":param");

        assertEquals(expected, builder.buildQueryPart().collect(Collectors.joining( )));
    }

    @Test
    public void itShouldSaveWherePartWithAdditionalConditions() {
        WhereBuilder builder = new WhereBuilder(queryGraph);
        FieldInfo field = FieldInfo.withAlias("t.field", "f");
        FieldInfo field2 = FieldInfo.withAlias("t.field2", "f2");
        FieldInfo field3 = FieldInfo.withAlias("t.field3", "f3");
        String expected = "where t.field > :param and t.field2 = :param2 or t.field3 <> 1";

        builder.where(field, Operator.MORE, ":param")
                .and(field2, Operator.EQUAL, ":param2")
                .or(field3, Operator.NOT_EQUAL, "1");

        assertEquals(expected, builder.buildQueryPart().collect(Collectors.joining(" ")));
    }
}
