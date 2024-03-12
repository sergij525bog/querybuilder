package org.oldman;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class JoinClause {
    private final JoinData joinData;
    private final JoinType joinType;
    private final FetchType fetchType;
    private final List<Condition> conditions = new LinkedList<>();

    private JoinClause(JoinData joinData, JoinType joinType, FetchType fetchType) {
        this.joinData = joinData;
        this.joinType = joinType;
        this.fetchType = fetchType;
    }

    void addCondition(Condition condition) {
        conditions.add(condition);
    }

    static JoinClause createJoinClause(JoinData joinData, JoinType joinType, FetchType fetchType) {
        if (joinData == null || joinType == null || fetchType == null) {
            throw new IllegalStateException("Builder has not enough info for creating new object");
        }
        return new JoinClause(joinData, joinType, fetchType);
    }

    String buildJoinClause() {
        String fetch = fetchType.getFetchType();
        String fetchString = fetch.isBlank() ? "" : " " + fetch;
        String joinClause = joinType.getJoinType() +
                fetchString +
                " " +
                joinData.buildJoinData();

        if (!conditions.isEmpty()) {
            String conditionString = conditions.stream()
                    .map(Condition::buildCondition)
                    .collect(Collectors.joining(" "));
            return joinClause + " " + conditionString;
        }

        return joinClause;
    }
}
