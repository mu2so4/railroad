package ru.nsu.ccfit.muratov.railroad.gui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import ru.nsu.ccfit.muratov.railroad.database.Row;
import ru.nsu.ccfit.muratov.railroad.database.Schema;
import ru.nsu.ccfit.muratov.railroad.database.table.Table;

import java.util.List;

public class ViewTableController {
    @FXML
    private TableView dataTable;
    @FXML
    private TextArea tableNameHeader;

    private Table table;

    public void setData(List<Row> rows, String tableName) {
        tableNameHeader.setText(tableName);
        table = Schema.getInstance().getTable(tableName);
        ObservableList<TableColumn> textViewColumns = dataTable.getColumns();
        textViewColumns.clear();
        for(var column: table.getColumns()) {
            TableColumn tableColumn = new TableColumn<>(column.getName());
            textViewColumns.add(tableColumn);
        }
    }
}
