module tssm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;

    opens tssm to javafx.fxml;
    opens views to javafx.fxml;
    exports tssm;
    exports views;
}


