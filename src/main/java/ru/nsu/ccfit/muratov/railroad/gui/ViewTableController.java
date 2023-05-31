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
import ru.nsu.ccfit.muratov.railroad.model.DatabaseException;
import ru.nsu.ccfit.muratov.railroad.model.Row;
import ru.nsu.ccfit.muratov.railroad.model.RowDeleter;
import ru.nsu.ccfit.muratov.railroad.model.Schema;
import ru.nsu.ccfit.muratov.railroad.model.column.Column;
import ru.nsu.ccfit.muratov.railroad.model.factory.ProductCreatorException;
import ru.nsu.ccfit.muratov.railroad.model.table.Table;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewTableController {
    @FXML
    private TableView dataTable;
    @FXML
    private TextArea tableNameHeader;
    @FXML
    private TextArea errorTextArea;
    @FXML
    private Button insertButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;

    private Table table;

    private Row oldValue;

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
        var items = dataTable.getItems();
        for(Row row: rows) {
            Map<String, TextField> textFields = new HashMap<>();
            for(Map.Entry<String, String> entry: row.getValues().entrySet()) {
                TextField field = new TextField();
                field.setEditable(false);
                field.setStyle("-fx-background-color: transparent;");
                if(table.isPrimaryKey(entry.getKey())) {
                    field.setStyle("-fx-background-color: transparent; -fx-font-weight: bold;");
                }
                field.setText(entry.getValue());
                textFields.put(entry.getKey(), field);
            }
            items.add(textFields);
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

    public void onDeleteButtonClick() {
        Map<String, TextField> rowMap = (Map<String, TextField>) dataTable.getSelectionModel().getSelectedItem();
        if(rowMap == null) {
            return;
        }
        int index = dataTable.getSelectionModel().getSelectedIndex();
        Map<String, String> key = new HashMap<>();
        for(Column column: table.getPrimaryKey()) {
            String name = column.getName();
            key.put(name, rowMap.get(name).getText());
        }
        Row keyRow = new Row(key);
        try {
            RowDeleter.deleteRow(table.getName(), keyRow);
            errorTextArea.setVisible(false);
            dataTable.getItems().remove(index);
        } catch (SQLException | IOException | DatabaseException | ProductCreatorException e) {
            setErrorMessage(e.getMessage());
        }
    }

    public void onUpdateButtonClick() {
        if(oldValue != null) {
            errorTextArea.setVisible(true);
            errorTextArea.setText("Уже идёт обновление строки");
            return;
        }
        errorTextArea.setVisible(false);
        Map<String, TextField> rowMap =
                (Map<String, TextField>) dataTable.getSelectionModel().getSelectedItem();
        if(rowMap == null) {
            return;
        }
        oldValue = new Row(textFieldMapToStringMap(rowMap));
        for(var entry: rowMap.entrySet()) {
            TextField textField = entry.getValue();
            if(table.columnIsUpdatable(entry.getKey())) {
                textField.setEditable(true);
            }
        }
    }

    private void setErrorMessage(String message) {
        errorTextArea.setVisible(true);
        errorTextArea.setText(message);
    }

    private static Map<String, String> textFieldMapToStringMap(Map<String, TextField> textFieldMap) {
        Map<String, String> stringMap = new HashMap<>();
        for(Map.Entry<String, TextField> entry: textFieldMap.entrySet()) {
            stringMap.put(entry.getKey(), entry.getValue().getText());
        }
        return stringMap;
    }
}
