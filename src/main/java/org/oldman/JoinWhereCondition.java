package org.oldman;

import org.oldman.enums.Operator;
import org.oldman.models.FieldInfo;

class JoinWhereCondition implements Condition {
    private final ConditionType conditionType;
    private final FieldInfo field;
    private final Operator operator;
    private final String parameter;

    JoinWhereCondition(ConditionType conditionType, FieldInfo field, Operator operator, String parameter) {
        this.conditionType = conditionType;
        this.field = field;
        this.operator = operator;
        this.parameter = parameter;
    }

    @Override
    public String buildCondition() {
        return conditionType.getConditionType() +
                " " +
                operator.apply(field, parameter);
    }
}
