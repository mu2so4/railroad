package ru.nsu.ccfit.muratov.railroad.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ru.nsu.ccfit.muratov.railroad.database.DatabaseException;
import ru.nsu.ccfit.muratov.railroad.database.OrderByList;
import ru.nsu.ccfit.muratov.railroad.database.Row;
import ru.nsu.ccfit.muratov.railroad.database.Schema;
import ru.nsu.ccfit.muratov.railroad.database.column.Column;
import ru.nsu.ccfit.muratov.railroad.database.table.Table;
import ru.nsu.ccfit.muratov.railroad.database.table.TableReader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SelectTableController {
    @FXML
    private ListView listOfTables;
    @FXML
    private TableView orderByTable;
    @FXML
    private TableColumn tableColumnColumn;
    @FXML
    private TableColumn orderByOptionColumn;
    @FXML
    private TableColumn priorityColumn;

    private String currentSelectedTable;

    public void onBackButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main-menu.fxml"));
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public ListView getListOfTables() {
        return listOfTables;
    }

    public void onSelectButtonClick(ActionEvent event) throws IOException,
            SQLException, DatabaseException {
        Object item = listOfTables.getSelectionModel().getSelectedItem();
        if(item == null) {
            return;
        }
        OrderByList orderByList = OrderByListProducer.createOrderByList(orderByTable);
        TableReader reader = new TableReader(currentSelectedTable);
        List<Row> rows = reader.fetchTable(orderByList);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/view-table.fxml"));
        Parent root = loader.load();
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        ViewTableController controller = loader.getController();
        controller.setData(rows, currentSelectedTable);
    }

    public void onListItemClick() {
        String item = (String) listOfTables.getSelectionModel().getSelectedItem();
        tableColumnColumn.setCellValueFactory(new PropertyValueFactory<>("rowName"));
        orderByOptionColumn.setCellValueFactory(new PropertyValueFactory<>("orderByOption"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));

        if(item != null && !item.equals(currentSelectedTable)) {
            currentSelectedTable = item;
            orderByTable.getItems().clear();
            Table table = Schema.getInstance().getTable(currentSelectedTable);
            var primaryKey = table.getPrimaryKey();
            for(Column column: table.getColumns()) {
                orderByTable.getItems().add(new OrderByOptionRow(column.getName(),
                        primaryKey.contains(column)));
            }
        }

    }
}
