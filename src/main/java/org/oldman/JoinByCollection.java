package org.oldman;

import org.oldman.models.FieldInfo;

class JoinByCollection implements JoinData {
    private final FieldInfo collectionField;

    JoinByCollection(FieldInfo collectionField) {
        this.collectionField = collectionField;
    }

    @Override
    public String buildJoinData() {
        return collectionField.getTableAlias() +
                "." +
                collectionField.getField() +
                " " +
                collectionField.getFieldAlias();
    }

    static class JoinByCollectionBuilder implements JoinDataBuilder {
        private FieldInfo collectionField;

        public JoinByCollectionBuilder collectionField(FieldInfo collectionField) {
            this.collectionField = collectionField;
            return this;
        }

        @Override
        public JoinData build() {
            if (collectionField == null) {
                throw new IllegalStateException("Builder has not enough info for creating new object");
            }
            return new JoinByCollection(collectionField);
        }
    }
}
