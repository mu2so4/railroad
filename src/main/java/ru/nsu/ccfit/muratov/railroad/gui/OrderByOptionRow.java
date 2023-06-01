package ru.nsu.ccfit.muratov.railroad.gui;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lombok.Getter;

@Getter
public class OrderByOptionRow {
    private final TextField rowName;
    private final ComboBox<String> orderByOption = new ComboBox<>();
    private final ComboBox<Integer> priority = new ComboBox<>();

    public static final String NO_SORT = "Не сортировать";
    public static final String ASCENDING = "По возрастанию";
    public static final String DESCENDING = "По убыванию";

    public OrderByOptionRow(String name, boolean isPrimaryKey) {
        rowName = new TextField();
        rowName.setText(name);
        rowName.setEditable(false);
        if(isPrimaryKey) {
            rowName.setStyle("-fx-font-weight: bold");
        }
        orderByOption.getItems().addAll(NO_SORT, ASCENDING, DESCENDING);

        priority.getItems().addAll(-1, 1, 2, 3);
    }
}
