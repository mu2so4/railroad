package ru.nsu.ccfit.muratov.railroad.gui;

import javafx.scene.control.TableView;
import ru.nsu.ccfit.muratov.railroad.model.OrderByList;
import ru.nsu.ccfit.muratov.railroad.model.OrderType;

import java.util.List;

public class OrderByListProducer {
    private OrderByListProducer() {}

    public static OrderByList createOrderByList(TableView table) {
        List<OrderByOptionRow> options = table.getItems();
        OrderByList orderByList = new OrderByList();
        options.sort((a, b) -> {
            Integer first = a.getPriority().getValue();
            Integer second = b.getPriority().getValue();
            if(first == null && second == null) {
                return 0;
            }
            if(first == null) {
                return 1;
            }
            if(second == null) {
                return -1;
            }
            return first - second;
        });
        for(int index = 0; index < options.size(); index++) {
            OrderByOptionRow row = options.get(index);
            Integer order = row.getPriority().getValue();
            if(order != null && order != -1 && orderByList.size() < order) {
                OrderType type = null;
                switch(row.getOrderByOption().getValue()) {
                    case OrderByOptionRow.ASCENDING -> type = OrderType.ASC;
                    case OrderByOptionRow.DESCENDING -> type = OrderType.DESC;
                }
                if (type != null) {
                    orderByList.add(row.getRowName().getText(), type);
                }
            }
        }
        return orderByList;
    }
}
