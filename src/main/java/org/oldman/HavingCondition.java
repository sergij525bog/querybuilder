package org.oldman;

import org.oldman.enums.HqlFunction;
import org.oldman.enums.Operator;
import org.oldman.models.FieldInfo;

class HavingCondition implements Condition {
    private final ConditionType conditionType;
    private final HqlFunction function;
    private final FieldInfo field;
    private final Operator operator;
    private final String parameter;

    HavingCondition(ConditionType conditionType, HqlFunction function, FieldInfo field, Operator operator, String parameter) {
        this.conditionType = conditionType;
        this.function = function;
        this.field = field;
        this.operator = operator;
        this.parameter = parameter;
    }

    @Override
    public String buildCondition() {
        return conditionType.getConditionType() + " " + operator.apply(function.apply(field), parameter);
    }
}
