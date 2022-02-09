package modelo;

import javafx.scene.control.Alert;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionDB {

    /**
     * Crea una conexión con la base de datos
     * @return Connection
     */
    public static Connection conectar() {
        Connection con = null;
        String url = "jdbc:postgresql://ec2-35-153-35-94.compute-1.amazonaws.com:5432/dd8kpht0jpvq85";
        String user = "phrwkhfzhkagve";
        String pwd = "3a3d6e9c733f55b9812042856661641418abc3934665cc76c9663da6d4513d33";

        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url,user,pwd);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return con;
    }
}
