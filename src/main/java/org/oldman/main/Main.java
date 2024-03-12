package org.oldman.main;

import org.oldman.*;
import org.oldman.enums.HqlFunction;
import org.oldman.enums.Operator;
import org.oldman.models.FieldInfo;
import org.oldman.models.TableInfo;

import java.lang.reflect.Field;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TableInfo fromTable = new TableInfo("Table", "t");
        FieldInfo fieldInfo = FieldInfo.withAlias("t.field", "f");
        String build = new QueryBuilder()
                .selectDistinct("t")
                .from(fromTable)
                .join(fieldInfo)
                    .with(fieldInfo, Operator.LESS, ":param")
                    .and(fieldInfo, Operator.MORE, ":param")
                .leftJoin(fromTable)
                    .on(fieldInfo, fieldInfo)
                    .or(fieldInfo, Operator.EQUAL, ":param")
                    .and(fieldInfo, Operator.NOT_EQUAL, ":param")
                .rightJoinFetch(fromTable)
                    .on(fieldInfo, fieldInfo).or(fieldInfo, Operator.NOT_EQUAL, ":param2")
                .where(fieldInfo, Operator.LESS_OR_EQUAL, ":param1")
                    .and(fieldInfo, Operator.MORE, ":param1")
                    .or(fieldInfo, Operator.NOT_EQUAL, ":param2")
                .groupBy(List.of(fieldInfo, fieldInfo))
                    .having(HqlFunction.COUNT, fieldInfo, Operator.LESS, ":param")
                    .and(HqlFunction.MAX, fieldInfo, Operator.LESS_OR_EQUAL, ":param")
                .orderBy(List.of(fieldInfo, fieldInfo, fieldInfo))
                .limit(5)
                .offset(5)
                .buildQueryString();
        System.out.println(build);
    }
}
