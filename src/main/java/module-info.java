module tims {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;

    opens tims to javafx.fxml;
    exports tims;
}
