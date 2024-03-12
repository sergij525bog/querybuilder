package org.oldman;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

class SelectBuilder extends BaseQueryPart {
    private static final String DISTINCT = "distinct";
    private final List<String> selectPart = new LinkedList<>();

    SelectBuilder() {
        super();
    }

    FromBuilder select(final String select) {
        return select(select, false);
    }

    FromBuilder selectDistinct(final String select) {
        return select(select, true);
    }

    private FromBuilder select(final String select, boolean distinct) {
        if (select == null || select.isBlank()) {
            throw new IllegalArgumentException("Select part cannot be blank");
        }

        this.selectPart.add(Clauses.SELECT.getClause());
        if (distinct) {
            this.selectPart.add(DISTINCT);
        }
        this.selectPart.add(select);

        queryGraph.addSelect(this);

        return new FromBuilder(this.queryGraph);
    }
    @Override
    protected Stream<String> buildQueryPart() {
        return selectPart.stream();
    }
}
