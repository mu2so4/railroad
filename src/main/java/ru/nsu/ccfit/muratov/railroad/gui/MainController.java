package ru.nsu.ccfit.muratov.railroad.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ru.nsu.ccfit.muratov.railroad.database.DatabaseException;
import ru.nsu.ccfit.muratov.railroad.database.table.Table;
import ru.nsu.ccfit.muratov.railroad.database.table.TableListReader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MainController {
    @FXML
    private Button exitButton;

    public void onExitClick() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void onEditDataClick(ActionEvent event) throws IOException, SQLException, DatabaseException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/select-table.fxml"));
        Parent root = loader.load();
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        SelectTableController controller = loader.getController();
        TableListReader reader = new TableListReader();
        List<Table> list = reader.loadTableList();
        var items = controller.getListOfTables().getItems();
        for(Table table: list) {
            items.add(table.getName());
        }
    }

    public void onPerformQueryClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/select-query.fxml"));
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onAboutClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/about.fxml"));
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
