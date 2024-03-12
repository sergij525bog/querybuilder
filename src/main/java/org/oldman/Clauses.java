package org.oldman;

public enum Clauses {
    SELECT("select"),
    FROM("from"),
    JOIN("join"),
    WHERE("where"),
    GROUP_BY("group by"),
    ORDER_BY("order by"),
    LIMIT("limit"),
    OFFSET("offset");

    private final String clause;

    Clauses(String clause) {
        this.clause = clause;
    }

    public String getClause() {
        return clause;
    }
}
