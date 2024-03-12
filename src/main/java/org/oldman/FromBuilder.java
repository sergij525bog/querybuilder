package org.oldman;

import org.oldman.models.TableInfo;

import java.util.stream.Stream;

public class FromBuilder extends BaseQueryPart {
    private TableInfo fromTable;

    protected FromBuilder(QueryGraph queryGraph) {
        super(queryGraph);
    }

    public JoinBuilder from(final TableInfo fromTable) {
        if (fromTable == null) {
            throw new IllegalArgumentException("From table cannot be null");
        }

        this.fromTable = fromTable;
        queryGraph.addFrom(this);

        return new JoinBuilder(this.queryGraph);
    }
    @Override
    protected Stream<String> buildQueryPart() {
        return Stream.of(Clauses.FROM.getClause(), fromTable.getInfo());
    }
}
