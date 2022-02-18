module tssm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;

    opens views to javafx.fxml;
    exports tssm;
    exports views;
    opens tssm to javafx.fxml;
    exports model;
    opens model to java.base;
}


