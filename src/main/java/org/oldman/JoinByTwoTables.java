package org.oldman;

import org.oldman.models.FieldInfo;
import org.oldman.models.TableInfo;

class JoinByTwoTables implements JoinData {
    private final FieldInfo firstField;
    private final FieldInfo secondField;
    private final TableInfo secondTable;

    JoinByTwoTables(FieldInfo firstField, FieldInfo secondField, TableInfo secondTable) {
        this.firstField = firstField;
        this.secondField = secondField;
        this.secondTable = secondTable;
    }

    @Override
    public String buildJoinData() {
        return secondTable.getInfo() +
                " on " +
                firstField.fieldWithTable() +
                " = " +
                secondField.fieldWithTable();
    }

    static class JoinByTwoTablesBuilder implements JoinDataBuilder {
        private FieldInfo firstField;
        private FieldInfo secondField;
        private TableInfo secondTable;

        public JoinByTwoTablesBuilder firstField(FieldInfo firstField) {
            this.firstField = firstField;
            return this;
        }

        public JoinByTwoTablesBuilder secondField(FieldInfo secondField) {
            this.secondField = secondField;
            return this;
        }

        public JoinByTwoTablesBuilder secondTable(TableInfo secondTable) {
            this.secondTable = secondTable;
            return this;
        }

        @Override
        public JoinData build() {
            if (firstField == null || secondField == null || secondTable == null) {
                throw new IllegalStateException("Builder has not enough info for creating new object");
            }
            return new JoinByTwoTables(firstField, secondField, secondTable);
        }
    }
}
