package ru.nsu.ccfit.muratov.railroad.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import ru.nsu.ccfit.muratov.railroad.database.DatabaseException;
import ru.nsu.ccfit.muratov.railroad.database.table.Table;
import ru.nsu.ccfit.muratov.railroad.database.table.TableListReader;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class SelectTableController {
    @FXML
    private ListView listOfTables;

    public SelectTableController() throws IOException, SQLException, DatabaseException {
        TableListReader reader = new TableListReader();
        List<Table> list = reader.loadTableList();
        var items = listOfTables.getItems();
        for(Table table: list) {
            items.add(table.getName());
        }
    }

    public void onBackButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main-menu.fxml"));
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
