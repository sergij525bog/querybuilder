package org.oldman;

import java.util.stream.Stream;

public class LimitBuilder extends BaseQueryPart implements CanBuildQuery {
    private int limit;

    LimitBuilder(QueryGraph queryGraph) {
        super(queryGraph);
    }

    public OffsetBuilder limit(int limit) {
        this.limit = limit;
        queryGraph.addLimit(this);

        return new OffsetBuilder(queryGraph);
    }

    public CanBuildQuery offset(int offset) {
        return new OffsetBuilder(queryGraph).offset(offset);
    }

    @Override
    protected Stream<String> buildQueryPart() {
        return Stream.of(Clauses.LIMIT.getClause(), String.valueOf(limit));
    }

    @Override
    public String buildQueryString() {
        return buildQuery();
    }
}
