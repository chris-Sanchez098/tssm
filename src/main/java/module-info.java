module tims {
    requires javafx.controls;
    requires javafx.fxml;

    opens tims to javafx.fxml;
    exports tims;
}
