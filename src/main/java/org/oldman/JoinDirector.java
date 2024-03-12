package org.oldman;

import org.oldman.models.FieldInfo;
import org.oldman.models.TableInfo;

class JoinDirector {
    static JoinData createJoinByCollection(FieldInfo fieldInfo) {
        return new JoinByCollection.JoinByCollectionBuilder()
                .collectionField(fieldInfo)
                .build();
    }

    static JoinData createJoinByTwoTables(FieldInfo firstField, FieldInfo secondField, TableInfo secondTable) {
        return new JoinByTwoTables.JoinByTwoTablesBuilder()
                .firstField(firstField)
                .secondField(secondField)
                .secondTable(secondTable)
                .build();
    }
}
