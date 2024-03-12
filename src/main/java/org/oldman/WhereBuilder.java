package org.oldman;

import org.oldman.enums.Operator;
import org.oldman.models.FieldInfo;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class WhereBuilder extends BaseQueryPart implements CanBuildQuery {

    private final LinkedList<Condition> whereData = new LinkedList<>();

    WhereBuilder(QueryGraph queryGraph) {
        super(queryGraph);
    }

    public GroupByBuilder.HavingPart groupBy(List<FieldInfo> fields) {
        return new GroupByBuilder(queryGraph).groupBy(fields);
    }

    public LimitBuilder orderBy(List<FieldInfo> orderList) {
        return new OrderByBuilder(queryGraph).orderBy(orderList);
    }

    public OffsetBuilder limit(int limit) {
        return new LimitBuilder(queryGraph).limit(limit);
    }
    public CanBuildQuery offset(int offset) {
        return new OffsetBuilder(queryGraph).offset(offset);
    }
    WherePart where(FieldInfo field, Operator operator, String parameter) {
        if (field == null || parameter == null) {
            throw new IllegalArgumentException("Field and parameter cant be null");
        }
        whereData.add(new JoinWhereCondition(ConditionType.WHERE, field, operator, parameter));
        queryGraph.addWhere(this);
        return new WherePart(queryGraph);
    }

    @Override
    protected Stream<String> buildQueryPart() {
        return whereData.stream().map(Condition::buildCondition);
    }

    @Override
    public String buildQueryString() {
        return buildQuery();
    }

    private void addCondition(Condition condition) {
        whereData.add(condition);
    }

    public final class WherePart extends WhereBuilder {
        private WherePart(QueryGraph queryGraph) {
            super(queryGraph);
        }

        public WherePart and(FieldInfo field, Operator operator, String parameter) {
            WhereBuilder.this.addCondition(new JoinWhereCondition(ConditionType.AND, field, operator, parameter));
            return this;
        }

        public WherePart or(FieldInfo field, Operator operator, String parameter) {
            WhereBuilder.this.addCondition(new JoinWhereCondition(ConditionType.OR, field, operator, parameter));
            return this;
        }
    }
}
