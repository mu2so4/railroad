package ru.nsu.ccfit.muratov.railroad.gui;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.Stage;
import ru.nsu.ccfit.muratov.railroad.model.Row;
import ru.nsu.ccfit.muratov.railroad.model.Schema;
import ru.nsu.ccfit.muratov.railroad.model.table.Table;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ViewTableController {
    @FXML
    private TableView dataTable;
    @FXML
    private TextArea tableNameHeader;
    @FXML
    private Button insertButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    private Table table;

    public void setData(List<Row> rows, String tableName) {
        tableNameHeader.setText(tableName);
        table = Schema.getInstance().getTable(tableName);
        ObservableList<TableColumn> textViewColumns = dataTable.getColumns();
        textViewColumns.clear();
        if(!table.isMutable()) {
            insertButton.setDisable(true);
            updateButton.setDisable(true);
            deleteButton.setDisable(true);
        }
        for(var column: table.getColumns()) {
            TableColumn<Map, String> tableColumn = new TableColumn<>(column.getName());
            tableColumn.setCellValueFactory(new MapValueFactory<>(column.getName()));
            textViewColumns.add(tableColumn);
        }
        for(Row row: rows) {
            for(Map.Entry<String, String> column: row.getValues().entrySet()) {
                TextField textField = new TextField();
                textField.setText(column.getValue());
                if(table.isPrimaryKey(column.getKey())) {
                    textField.setStyle("-fx-font-weight: bold");
                }
            }
            dataTable.getItems().add(row.getValues());
        }
    }

    public void onBackButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main-menu.fxml"));
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
