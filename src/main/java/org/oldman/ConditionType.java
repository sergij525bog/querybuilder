package org.oldman;

enum ConditionType {
    WHERE("where"),
    AND("and"),
    OR("or"),
    WITH("with"),
    HAVING("having");

    private final String conditionType;

    ConditionType(String conditionType) {
        this.conditionType = conditionType;
    }

    String getConditionType() {
        return conditionType;
    }
}
