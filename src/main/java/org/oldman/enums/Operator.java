package org.oldman.enums;

import org.oldman.models.FieldInfo;

public enum Operator {
    EQUAL("="),
    NOT_EQUAL("<>"),
    LESS("<"),
    MORE(">"),
    LESS_OR_EQUAL("<="),
    MORE_OR_EQUAL(">=");

    private final String operator;

    Operator(String operator) {
        this.operator = operator;
    }

    public String apply(FieldInfo field, String parameter) {
        return field.fieldWithTable() + " " + operator + " " + parameter;
    }

    public String apply(String parameter1, String parameter2) {
        return parameter1 + " " + operator + " " + parameter2;
    }
}
