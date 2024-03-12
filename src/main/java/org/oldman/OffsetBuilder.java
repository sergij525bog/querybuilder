package org.oldman;

import java.util.stream.Stream;

public class OffsetBuilder extends BaseQueryPart implements CanBuildQuery {
    private int offset;

    OffsetBuilder(QueryGraph queryGraph) {
        super(queryGraph);
    }

    public CanBuildQuery offset(int offset) {
        this.offset = offset;

        queryGraph.addOffset(this);

        return this;
    }

    @Override
    public String buildQueryString() {
        return buildQuery();
    }

    @Override
    protected Stream<String> buildQueryPart() {
        return Stream.of(Clauses.OFFSET.getClause(), String.valueOf(offset));
    }
}
