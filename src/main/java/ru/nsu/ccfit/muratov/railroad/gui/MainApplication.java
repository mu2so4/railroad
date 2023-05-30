package ru.nsu.ccfit.muratov.railroad.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root =
                FXMLLoader.load(getClass().getClassLoader().getResource("main-menu.fxml"));
        Scene scene = new Scene(root, 800, 800);
        stage.setTitle("Railroad");
        stage.setScene(scene);
        stage.show();
    }
}
