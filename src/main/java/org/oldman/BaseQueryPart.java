package org.oldman;

import java.util.EnumMap;
import java.util.stream.Stream;

public abstract class BaseQueryPart implements QueryPart {
    protected final QueryGraph queryGraph;

    protected BaseQueryPart() {
        queryGraph = new QueryGraph(new EnumMap<>(Clauses.class));
    }

    protected BaseQueryPart(QueryGraph queryGraph) {
        this.queryGraph = queryGraph;
    }

    protected abstract Stream<String> buildQueryPart();

    final String buildQuery() {
        return queryGraph.buildQuery();
    }
}
