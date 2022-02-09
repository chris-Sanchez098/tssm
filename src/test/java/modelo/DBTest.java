package modelo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class DBTest {
    static Connection con;

    @BeforeAll
    static void init() { con = ConexionDB.conectar();}

    @Test
    @DisplayName("Conexión test")
    public void connectionTest() {
        ConexionDB.conectar();
    }

    @Test
    @DisplayName("Desconexión test")
    public void disconnectTest() throws SQLException {
        con.close();
    }
}

