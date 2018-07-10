package al_qalam;

import java.awt.Dimension;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.swing.JRViewer;
import javax.swing.*;
import java.sql.Connection;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class ReportView extends JFrame
{
/**
 * This Method can be called from any class for making a Report and showing it on a JFrame with JRViewer, The Parameter Requires a Connection Object
 * , A Map Object of Parameters to be passed, The Report File Name to be shown, The Title to be shown on JFrame and The Size of the JFrame.
 * @param conn - The Connection Object
 * @param parameters - Map Object of Parameters to be Passed
 * @param reportfilename - The String Variable for Report File Name
 * @param title - The Title to be appeared on JFrame
 * @param size  - The Size of the JFrame in Dimension Object
 */
    public void showReport(Connection conn,Map parameters,String reportfilename,String title,Dimension size)
    {
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(reportfilename);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            //for print 
            //parameters holds the parameter passs to the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, conn);
            //Viewer Window to show
            JRViewer viewer = new JRViewer(jasperPrint);
            viewer.setOpaque(true);
            viewer.setVisible(true);
            //make your JFrame visible
            this.add(viewer);
            this.setSize(size);
            this.setVisible(true);
            this.setTitle(title);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(rootPane, ex+"AN ERROR OCCURED WHILE MAKING REPORT, CONTACT SOFTWARE DEVELOPER FOR FURTHER ASSISTANCE");
        }
    }
}