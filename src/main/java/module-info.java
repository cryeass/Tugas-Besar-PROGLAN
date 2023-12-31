module com.example.hellofx {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.hellofxtubes to javafx.fxml;
    exports com.example.hellofxtubes;
}