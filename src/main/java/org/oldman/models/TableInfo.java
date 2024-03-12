package org.oldman.models;

import org.oldman.models.Info;

public class TableInfo implements Info {
    private final String table;
    private final String alias;

    public TableInfo(String table, String alias) {
        this.table = table;
        this.alias = alias;
    }

    @Override
    public String getInfo() {
        return table + " " + alias;
    }

    public String getTable() {
        return table;
    }

    public String getAlias() {
        return alias;
    }
}
