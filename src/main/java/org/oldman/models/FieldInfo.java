package org.oldman.models;

public class FieldInfo implements Info {
    private final String tableAlias;

    private final String field;
    private final String fieldAlias;

    public FieldInfo(String tableAlias, String field) {
        this(tableAlias, field, null);
    }

    public FieldInfo(String tableAlias, String field, String fieldAlias) {
        this.tableAlias = tableAlias;
        this.field = field;
        this.fieldAlias = fieldAlias;
    }

    public static FieldInfo withAlias(String aliasWithField, String fieldAlias) {
        String[] split = aliasWithField.split("\\.");
        int length = split.length;
        if (length < 2 || length > 3) {
            throw new IllegalArgumentException("Cannot split input string on table alias and column name");
        }
        if (length == 3) {
            split[1] = split[1] + "." + split[2];
        }
        return new FieldInfo(split[0], split[1], fieldAlias);
    }

    public String getTableAlias() {
        return tableAlias;
    }

    public String getField() {
        return field;
    }

    public String getFieldAlias() {
        return fieldAlias;
    }

    public String fieldWithTable() {
        return tableAlias + "." + field;
    }

    private String fullFieldInfo() {
        return fieldWithTable() + " " + fieldAlias;
    }

    @Override
    public String getInfo() {
        return fieldAlias == null ? fieldWithTable() : fullFieldInfo();
    }
}
