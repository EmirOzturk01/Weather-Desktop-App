module com.example.aaaaaaaaaaaaa {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires json.simple;
    requires java.desktop;

    opens com.example.aaaaaaaaaaaaa to javafx.fxml;
    exports com.example.aaaaaaaaaaaaa;
}