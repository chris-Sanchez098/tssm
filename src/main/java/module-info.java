module tims {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;

    opens tssm to javafx.fxml;
    exports tssm;
}
