package org.oldman;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.oldman.enums.Operator;
import org.oldman.models.FieldInfo;
import org.oldman.models.TableInfo;

import java.util.EnumMap;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JoinBuilderTest {
    private static QueryGraph queryGraph;

    @BeforeEach
    public void setup() {
        queryGraph = new QueryGraph(new EnumMap<>(Clauses.class));
    }

    @Test
    public void itShouldFailIfJoinTableIsNull() {
        JoinBuilder builder = new JoinBuilder(queryGraph);

        assertThrows(IllegalArgumentException.class, () -> builder.join((TableInfo) null));
    }

    @Test
    public void itShouldFailIfAnyJoinFieldIsNull() {
        JoinBuilder builder = new JoinBuilder(queryGraph);
        FieldInfo joinField = FieldInfo.withAlias("t.field", "f");
        TableInfo joinTable = new TableInfo("Table", "t");

        assertThrows(IllegalArgumentException.class, () -> builder
                .join(joinTable)
                .on(joinField, null));
        assertThrows(IllegalArgumentException.class, () -> builder
                .join(joinTable)
                .on(null, joinField));
    }

    @Test
    public void itShouldFailIfJoinFieldsAreEqual() {
        JoinBuilder builder = new JoinBuilder(queryGraph);
        FieldInfo joinField = FieldInfo.withAlias("t.field", "f");
        TableInfo joinTable = new TableInfo("Table", "t");

        assertThrows(IllegalArgumentException.class, () -> builder
                .join(joinTable)
                .on(joinField, joinField));
    }

    @Test
    public void itShouldSaveJoinByTablePart() {
        JoinBuilder builder = new JoinBuilder(queryGraph);
        FieldInfo joinField = FieldInfo.withAlias("t.field", "f");
        FieldInfo joinField2 = FieldInfo.withAlias("t2.field", "f2");
        TableInfo joinTable = new TableInfo("Table", "t");
        String expected = "join Table t on t.field = t2.field";

        var join = builder.join(joinTable).on(joinField, joinField2);
        String joinPart = builder.buildQueryPart().collect(Collectors.joining(" "));

        assertEquals(builder, join.queryGraph.getJoin());
        assertEquals(expected, joinPart);
    }

    @Test
    public void itShouldSaveJoinByTableWithAdditionalConditions() {
        JoinBuilder builder = new JoinBuilder(queryGraph);
        FieldInfo joinField = FieldInfo.withAlias("t.field", "f");
        FieldInfo joinField2 = FieldInfo.withAlias("t2.field", "f2");
        TableInfo joinTable = new TableInfo("Table", "t");
        String expected = "join Table t on t.field = t2.field and t.field <> :param or t2.field > :param2";

        var join = builder.join(joinTable)
                .on(joinField, joinField2)
                .and(joinField, Operator.NOT_EQUAL, ":param")
                .or(joinField2, Operator.MORE, ":param2");
        String joinPart = builder.buildQueryPart().collect(Collectors.joining(" "));

        assertEquals(builder, join.queryGraph.getJoin());
        assertEquals(expected, joinPart);
    }

    @Test
    public void itShouldFailIfJoinCollectionIsNull() {
        JoinBuilder builder = new JoinBuilder(queryGraph);

        assertThrows(IllegalArgumentException.class, () -> builder.join((FieldInfo) null));
    }


    @Test
    public void itShouldSaveJoinWithCollection() {
        JoinBuilder builder = new JoinBuilder(queryGraph);

        builder.join(FieldInfo.withAlias("t.collection", "c"));

        assertEquals(builder, builder.queryGraph.getJoin());
    }

    @Test
    public void itShouldSaveJoinWithCollectionWithAdditionalConditions() {
        JoinBuilder builder = new JoinBuilder(queryGraph);
        FieldInfo collectionField = FieldInfo.withAlias("t.collection", "c");
        FieldInfo collectionTableField = FieldInfo.withAlias("c.field", "f");
        String expected = "join t.collection c with c.field = 1 or c.field > 111";

        builder.join(collectionField)
                .with(collectionTableField, Operator.EQUAL, "1")
                .or(collectionTableField, Operator.MORE, "111");



        assertEquals(builder, builder.queryGraph.getJoin());
        assertEquals(expected, builder.buildQueryPart().collect(Collectors.joining( )));
    }
}
