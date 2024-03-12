package org.oldman;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class SelectBuilderTest {
    @Test
    public void itShouldFailIfSelectStringIsBlank() {
        SelectBuilder builder = new SelectBuilder();
        assertThrows(IllegalArgumentException.class, () -> builder.select(null));
        assertThrows(IllegalArgumentException.class, () -> builder.select(""));
        assertThrows(IllegalArgumentException.class, () -> builder.select(" \n\t"));
    }

    @Test
    public void itShouldSaveSelectPartSuccessfully() {
        SelectBuilder builder = new SelectBuilder();
        String expected = "expected";

        builder.select(expected);
        List<String> selectPart = builder.buildQueryPart().collect(Collectors.toList());

        assertEquals(Clauses.SELECT.getClause(), selectPart.get(0));
        assertEquals(expected, selectPart.get(1));
    }

    @Test
    public void itShouldSaveSelectDistinctPartSuccessfully() {
        SelectBuilder builder = new SelectBuilder();
        String expected = "expected";

        builder.selectDistinct(expected);
        List<String> selectPart = builder.buildQueryPart().collect(Collectors.toList());

        assertEquals(Clauses.SELECT.getClause(), selectPart.get(0));
        assertEquals("distinct", selectPart.get(1));
        assertEquals(expected, selectPart.get(2));
    }
}
