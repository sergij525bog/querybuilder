package org.oldman;

import org.oldman.enums.HqlFunction;
import org.oldman.enums.Operator;
import org.oldman.models.FieldInfo;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroupByBuilder extends BaseQueryPart implements CanBuildQuery {
    private final List<FieldInfo> groupByList = new LinkedList<>();
    private List<Condition> conditions = new LinkedList<>();

    private GroupByBuilder(QueryGraph queryGraph, List<Condition> conditions) {
        super(queryGraph);
        this.conditions = conditions;
    }

    GroupByBuilder(QueryGraph queryGraph) {
        super(queryGraph);
    }

    HavingPart groupBy(List<FieldInfo> fields) {
        groupByList.addAll(fields);
        queryGraph.addGroupBy(this);
        return new HavingPart(this.queryGraph, conditions);
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

    @Override
    protected Stream<String> buildQueryPart() {
        return Stream.concat(
                Stream.concat(
                        Stream.of(Clauses.GROUP_BY.getClause()),
                        Stream.of(groupByList.stream()
                                .map(FieldInfo::fieldWithTable)
                                .collect(Collectors.joining(", ")))),
                conditions.stream().map(Condition::buildCondition)
        );
    }

    @Override
    public String buildQueryString() {
        return buildQuery();
    }

    protected void addCondition(Condition condition) {
        conditions.add(condition);
    }

    public final class HavingPart extends GroupByBuilder {
        private HavingPart(QueryGraph queryGraph, List<Condition> conditions) {
            super(queryGraph, conditions);
        }

        public HavingPart.AndOr having(HqlFunction function, FieldInfo field, Operator operator, String parameter) {
            addCondition(new HavingCondition(ConditionType.HAVING, function, field, operator, parameter));
            return new AndOr(this.queryGraph, conditions);
        }
    }

    public final class AndOr extends GroupByBuilder {
        private AndOr(QueryGraph queryGraph, List<Condition> conditions) {
            super(queryGraph, conditions);
        }

        public AndOr and(HqlFunction function, FieldInfo field, Operator operator, String parameter) {
            addCondition(new HavingCondition(ConditionType.AND, function, field, operator, parameter));
            return this;
        }

        public AndOr or(HqlFunction function, FieldInfo field, Operator operator, String parameter) {
            addCondition(new HavingCondition(ConditionType.OR, function, field, operator, parameter));
            return this;
        }
    }
}
