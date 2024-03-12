package org.oldman;

public class QueryBuilder {
    public FromBuilder select(final String select) {
        return new SelectBuilder().select(select);
    }

    public FromBuilder selectDistinct(final String select) {
        return new SelectBuilder().selectDistinct(select);
    }
}
