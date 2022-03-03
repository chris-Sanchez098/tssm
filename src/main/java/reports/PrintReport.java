package reports;
import model.ConexionDB;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

public class PrintReport {
    /**
     * open a window with the report
     * @param nameFile in reports
     * @param query to search in db
     */
    public void showReport(String nameFile, String query){
        try{
            String file = "src/main/resources/reports/"+ nameFile + ".jrxml";
            JasperDesign jasperDesign = JRXmlLoader.load(new File(file));
            Connection conn = ConexionDB.connect();
            JRDesignQuery jrDesignQuery = new JRDesignQuery();
            jrDesignQuery.setText(query);
            jasperDesign.setQuery(jrDesignQuery);

            JasperReport report = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint print = JasperFillManager.fillReport(report, null, conn);
            JasperViewer viewer = new JasperViewer(print, false);
            viewer.setVisible(true);
            viewer.setSize(850,600);
            conn.close();

        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
