package org.oldman;

import org.oldman.enums.Operator;
import org.oldman.models.FieldInfo;
import org.oldman.models.TableInfo;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class JoinBuilder extends BaseQueryPart implements CanBuildQuery {
    private final LinkedList<JoinClause> joinClauses;

    JoinBuilder(QueryGraph queryGraph) {
        super(queryGraph);
        joinClauses = new LinkedList<>();
    }

    private JoinBuilder(QueryGraph queryGraph, LinkedList<JoinClause> joinClauses) {
        super(queryGraph);
        this.joinClauses = joinClauses;
    }

    public WithPart join(final FieldInfo collection) {
        return joinWith(collection, JoinType.JOIN);
    }

    public JoinBuilder joinFetch(final FieldInfo collection) {
        join(collection, JoinType.JOIN, FetchType.FETCH);
        return this;
    }

    public WithPart leftJoin(final FieldInfo collection) {
        return joinWith(collection, JoinType.LEFT_JOIN);
    }

    public JoinBuilder leftJoinFetch(final FieldInfo collection) {
        join(collection, JoinType.LEFT_JOIN, FetchType.FETCH);
        return this;
    }

    public WithPart rightJoin(final FieldInfo collection) {
        return joinWith(collection, JoinType.RIGHT_JOIN);
    }

    public JoinBuilder rightJoinFetch(final FieldInfo collection) {
        join(collection, JoinType.RIGHT_JOIN, FetchType.FETCH);
        return this;
    }

    private WithPart joinWith(FieldInfo collection, JoinType joinType) {
        join(collection, joinType, FetchType.NONE);
        return new WithPart(this);
    }

    private void join(FieldInfo collection, JoinType joinType, FetchType fetchType) {
        if (collection == null) {
            throw new IllegalArgumentException("Collection field cannot be null");
        }

        join(JoinDirector.createJoinByCollection(collection), joinType, fetchType);
    }

    public OnPart join(TableInfo joinTable) {
        return join(joinTable, JoinType.JOIN, FetchType.NONE);
    }

    public OnPart joinFetch(TableInfo joinTable) {
        return join(joinTable, JoinType.JOIN, FetchType.FETCH);
    }

    public OnPart leftJoin(TableInfo joinTable) {
        return join(joinTable, JoinType.LEFT_JOIN, FetchType.NONE);
    }

    public OnPart leftJoinFetch(TableInfo joinTable) {
        return join(joinTable, JoinType.LEFT_JOIN, FetchType.FETCH);
    }

    public OnPart rightJoin(TableInfo joinTable) {
        return join(joinTable, JoinType.RIGHT_JOIN, FetchType.NONE);
    }

    public OnPart rightJoinFetch(TableInfo joinTable) {
        return join(joinTable, JoinType.RIGHT_JOIN, FetchType.FETCH);
    }

    private OnPart join(TableInfo joinTable, JoinType joinType, FetchType fetchType) {
        if (joinTable == null) {
            throw new IllegalArgumentException("Join table cannot be null");
        }

        return new OnPart(this, new OnPart.TempJoinInfo(joinTable, joinType, fetchType));
    }

    public WhereBuilder.WherePart where(FieldInfo field, Operator operator, String parameter) {
        return new WhereBuilder(queryGraph).where(field, operator, parameter);
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

    private void join(final JoinData joinData, JoinType joinType, FetchType fetchType) {
        if (queryGraph.getJoin() == null) {
            queryGraph.addJoin(this);
        }
        joinClauses.add(JoinClause.createJoinClause(joinData, joinType, fetchType));
    }
    @Override
    protected Stream<String> buildQueryPart() {
        return joinClauses.stream().map(JoinClause::buildJoinClause);
    }

    @Override
    public String buildQueryString() {
        return buildQuery();
    }

    void addCondition(Condition condition) {
        joinClauses.getLast().addCondition(condition);
    }

    public final static class WithPart {
        private final JoinBuilder builder;
        private WithPart(JoinBuilder builder) {
            this.builder = builder;
        }

        public AndOr with(FieldInfo field, Operator operator, String parameter) {
            builder.addCondition(new JoinWhereCondition(ConditionType.WITH, field, operator, parameter));
            return new AndOr(builder);
        }
    }

    public final static class OnPart {
        private final JoinBuilder builder;
        private final TempJoinInfo tempInfo;

        private OnPart(JoinBuilder builder, TempJoinInfo tempInfo) {
            this.builder = builder;
            this.tempInfo = tempInfo;
        }

        public AndOr on(final FieldInfo firstField, final FieldInfo secondField) {
            if (firstField == null || secondField == null) {
                throw new IllegalArgumentException("Join fields cant be null");
            }
            if (firstField == secondField) {
                throw new IllegalArgumentException("Fields cant be equal");
            }

            builder.join(
                    JoinDirector.createJoinByTwoTables(firstField,secondField, tempInfo.tableInfo),
                    tempInfo.joinType,
                    tempInfo.fetchType
            );
            return new AndOr(builder);
        }

        private final static class TempJoinInfo {
            private final TableInfo tableInfo;
            private final JoinType joinType;
            private final FetchType fetchType;

            private TempJoinInfo(TableInfo tableInfo, JoinType joinType, FetchType fetchType) {
                this.tableInfo = tableInfo;
                this.joinType = joinType;
                this.fetchType = fetchType;
            }
        }
    }

    public final static class AndOr extends JoinBuilder {
        private AndOr(JoinBuilder builder) {
            super(builder.queryGraph, builder.joinClauses);
        }

        public AndOr and(FieldInfo field, Operator operator, String parameter) {
            addCondition(new JoinWhereCondition(ConditionType.AND, field, operator, parameter));
            return this;
        }

        public AndOr or(FieldInfo field, Operator operator, String parameter) {
            addCondition(new JoinWhereCondition(ConditionType.OR, field, operator, parameter));
            return this;
        }
    }
}
