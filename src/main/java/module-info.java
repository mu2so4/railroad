module ru.nsu.ccfit.muratov.railroad {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;
    requires static lombok;

    opens ru.nsu.ccfit.muratov.railroad.gui to javafx.fxml;
    exports ru.nsu.ccfit.muratov.railroad.gui;
}