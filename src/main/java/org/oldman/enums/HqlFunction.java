package org.oldman.enums;

import org.oldman.models.FieldInfo;

public enum HqlFunction {
    COUNT("count"),
    MAX("max"),
    MIN("min"),
    AVG("avg"),
    SUM("sum");

    private final String function;

    HqlFunction(String function) {
        this.function = function;
    }

    public String apply(FieldInfo field) {
        return function + "(" + field.fieldWithTable() + ")";
    }
}
