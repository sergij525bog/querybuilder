package org.oldman;

import org.oldman.models.FieldInfo;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderByBuilder extends BaseQueryPart implements CanBuildQuery {
    private final List<FieldInfo> orderList = new LinkedList<>();

    OrderByBuilder(QueryGraph queryGraph) {
        super(queryGraph);
    }

    public LimitBuilder orderBy(List<FieldInfo> orderList) {
        this.orderList.addAll(orderList);

        queryGraph.addOrderBy(this);

        return new LimitBuilder(queryGraph);
    }

    public OffsetBuilder limit(int limit) {
        return new LimitBuilder(queryGraph).limit(limit);
    }
    public CanBuildQuery offset(int offset) {
        return new OffsetBuilder(queryGraph).offset(offset);
    }

    @Override
    protected Stream<String> buildQueryPart() {
        return Stream.of(
                Clauses.ORDER_BY.getClause(),
                orderList.stream()
                        .map(FieldInfo::fieldWithTable)
                        .collect(Collectors.joining(", "))
        );
    }

    @Override
    public String buildQueryString() {
        return buildQuery();
    }
}
