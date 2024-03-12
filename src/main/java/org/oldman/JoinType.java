package org.oldman;

enum JoinType {
    JOIN("join"),
    LEFT_JOIN("left join"),
    RIGHT_JOIN("right join");

    private final String joinType;

    JoinType(String joinType) {
        this.joinType = joinType;
    }

    String getJoinType() {
        return joinType;
    }
}
