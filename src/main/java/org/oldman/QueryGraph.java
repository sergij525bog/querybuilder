package org.oldman;

import java.util.EnumMap;
import java.util.stream.Collectors;

public class QueryGraph {
    private final EnumMap<Clauses, BaseQueryPart> queryClauses;

    QueryGraph(EnumMap<Clauses, BaseQueryPart> queryClauses) {
        this.queryClauses = queryClauses;
    }

    String buildQuery() {
        return queryClauses.values().stream()
                .flatMap(BaseQueryPart::buildQueryPart)
                .collect(Collectors.joining(" "));
    }

    void addSelect(BaseQueryPart queryPart) {
        queryClauses.put(Clauses.SELECT, queryPart);
    }

    void addFrom(BaseQueryPart queryPart) {
        queryClauses.put(Clauses.FROM, queryPart);
    }

    void addJoin(BaseQueryPart queryPart) {
        queryClauses.put(Clauses.JOIN, queryPart);
    }

    void addWhere(BaseQueryPart queryPart) {
        queryClauses.put(Clauses.WHERE, queryPart);
    }

    void addGroupBy(BaseQueryPart queryPart) {
        queryClauses.put(Clauses.GROUP_BY, queryPart);
    }

    void addOrderBy(BaseQueryPart queryPart) {
        queryClauses.put(Clauses.ORDER_BY, queryPart);
    }

    void addLimit(BaseQueryPart queryPart) {
        queryClauses.put(Clauses.LIMIT, queryPart);
    }

    void addOffset(BaseQueryPart queryPart) {
        queryClauses.put(Clauses.OFFSET, queryPart);
    }

    JoinBuilder getJoin() {
        return (JoinBuilder) queryClauses.get(Clauses.JOIN);
    }
}
