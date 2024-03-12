package org.oldman;

enum FetchType {
    FETCH("fetch"),
    NONE("");

    private final String fetchType;

    FetchType(String fetchType) {
        this.fetchType = fetchType;
    }

    String getFetchType() {
        return fetchType;
    }
}
