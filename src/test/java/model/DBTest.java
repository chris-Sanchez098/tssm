package model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DBTest {
    static Connection con;

    @BeforeAll
    static void init() { con = ConexionDB.connect();}

    @Test
    @DisplayName("Conexión test")
    public void connectionTest() {
        con = ConexionDB.connect();
        assertNotNull(con);
    }

    @Test
    @DisplayName("Desconexión test")
    public void disconnectTest() throws SQLException {
        con.close();
    }
}

