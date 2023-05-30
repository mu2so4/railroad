package ru.nsu.ccfit.muratov.railroad.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class SelectTableController {
    @FXML
    private ListView listOfTables;

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

    public void onSelectButtonClick() {
        Object item = listOfTables.getSelectionModel().getSelectedItem();
        if(item == null) {
            return;
        }
        System.out.println(item);
    }
}
