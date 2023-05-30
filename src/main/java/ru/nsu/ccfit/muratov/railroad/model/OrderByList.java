package ru.nsu.ccfit.muratov.railroad.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class OrderByList {
    private final Map<Integer, SortOption> order = new HashMap<>();

    public OrderByList() {}

    public OrderByList(SortOption[] columns) {
        for(SortOption sortOption: columns) {
            add(sortOption);
        }
    }

    public String makeOrderByStatement() {
        if(order.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder(" ORDER BY ");
        for(Map.Entry<Integer, SortOption> entry: order.entrySet()) {
            builder.append(String.format(" \"%s\" %s, ", entry.getValue().columnName,
                    entry.getValue().orderType.getDisplayName()));
        }
        builder.deleteCharAt(builder.length() - 2);
        return builder.toString();
    }

    public void add(String columnName, OrderType type) {
        order.put(order.size(), new SortOption(columnName, type));
    }

    public void add(String columnName) {
        order.put(order.size(), new SortOption(columnName));
    }

    public void add(SortOption sortOption) {
        order.put(order.size(), sortOption);
    }

    public int size() {
        return order.size();
    }

    @Setter
    @Getter
    @AllArgsConstructor
    public static class SortOption {
        String columnName;
        OrderType orderType;

        public SortOption(String columnName) {
            this.columnName = columnName;
            this.orderType = OrderType.ASC;
        }
    }
}
