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
import ru.nsu.ccfit.muratov.railroad.model.*;
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
    private Map<String, TextField> selectedRow;

    public void setData(List<Row> rows, String tableName) {
        tableNameHeader.setText(tableName);
        table = Schema.getInstance().getTable(tableName);
        ObservableList<TableColumn> textViewColumns = dataTable.getColumns();
        textViewColumns.clear();
        if (!table.isMutable()) {
            insertButton.setDisable(true);
            updateButton.setDisable(true);
            deleteButton.setDisable(true);
        }
        for (var column : table.getColumns()) {
            TableColumn<Map, String> tableColumn = new TableColumn<>(column.getName());
            tableColumn.setCellValueFactory(new MapValueFactory<>(column.getName()));
            textViewColumns.add(tableColumn);
        }
        var items = dataTable.getItems();
        for (Row row : rows) {
            Map<String, TextField> textFields = new HashMap<>();
            for (Map.Entry<String, String> entry : row.getValues().entrySet()) {
                TextField field = new TextField();
                field.setEditable(false);
                field.setStyle("-fx-background-color: transparent;");
                if (table.isPrimaryKey(entry.getKey())) {
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
        if (rowMap == null) {
            return;
        }
        int index = dataTable.getSelectionModel().getSelectedIndex();
        Map<String, String> key = new HashMap<>();
        for (Column column : table.getPrimaryKey()) {
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
        if(selectedRow != null) {
            setErrorMessage("Уже идёт обновление или вставка данных");
            return;
        }
        errorTextArea.setVisible(false);
        selectedRow =
                (Map<String, TextField>) dataTable.getSelectionModel().getSelectedItem();
        if (selectedRow == null) {
            return;
        }
        oldValue = new Row(textFieldMapToStringMap(selectedRow));
        for(var entry : selectedRow.entrySet()) {
            TextField textField = entry.getValue();
            String columnName = entry.getKey();
            if (table.columnIsUpdatable(columnName) && !table.isPrimaryKey(columnName)) {
                textField.setEditable(true);
            }
        }
        enableConfirmButtons();
    }

    public void onInsertButtonClick() {
        if(selectedRow != null) {
            setErrorMessage("Уже идёт обновление или вставка данных");
            return;
        }
        selectedRow = new HashMap<>();
        for(Column column: table.getColumns()) {
            selectedRow.put(column.getName(), new TextField());
        }
        dataTable.getItems().add(0, selectedRow);
        dataTable.getSelectionModel().focus(0);
        dataTable.scrollTo(0);
        enableConfirmButtons();
    }

    public void onConfirmButtonClick() {
        disableConfirmButtons();
        if(oldValue != null) {
            Map<String, String> keyMap = new HashMap<>();
            for(Column column: table.getPrimaryKey()) {
                String name = column.getName();
                keyMap.put(name, oldValue.getValues().get(name));
            }
            Map<String, String> newValues = new HashMap<>();
            for(Column column: table.getColumns()) {
                String name = column.getName();
                TextField field = selectedRow.get(name);
                field.setEditable(false);
                String newValue = field.getText();
                if(!oldValue.get(name).equals(newValue)) {
                    newValues.put(name, newValue);
                }
            }
            try {
                RowUpdater.updateRow(table.getName(), new Row(keyMap), new Row(newValues));
                errorTextArea.setVisible(false);
            }
            catch(SQLException | ProductCreatorException | IOException | DatabaseException e) {
                setErrorMessage(e.getMessage());
                rollbackUpdate();
            }
            selectedRow = null;
            oldValue = null;
        }
        else {
            //todo inserting
        }
    }

    public void onCancelButtonClick() {
        disableConfirmButtons();
        if(oldValue != null) {
            for(Column column: table.getColumns()) {
                String name = column.getName();
                TextField field = selectedRow.get(name);
                field.setEditable(false);
            }
            rollbackUpdate();
        }
        else {
            //todo inserting
        }
    }

    private void setErrorMessage(String message) {
        errorTextArea.setVisible(true);
        errorTextArea.setText(message);
    }

    private void rollbackUpdate() {
        for(Map.Entry<String, TextField> entry: selectedRow.entrySet()) {
            String columnName = entry.getKey();
            TextField field = selectedRow.get(columnName);
            field.setText(oldValue.get(columnName));
        }
        selectedRow = null;
        oldValue = null;
    }

    private void enableConfirmButtons() {
        confirmButton.setDisable(false);
        cancelButton.setDisable(false);
    }

    private void disableConfirmButtons() {
        confirmButton.setDisable(true);
        cancelButton.setDisable(true);
    }

    private static Map<String, String> textFieldMapToStringMap(Map<String, TextField> textFieldMap) {
        Map<String, String> stringMap = new HashMap<>();
        for(Map.Entry<String, TextField> entry: textFieldMap.entrySet()) {
            stringMap.put(entry.getKey(), entry.getValue().getText());
        }
        return stringMap;
    }
}
