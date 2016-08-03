/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package its5201finalproject;

import static its5201finalproject.FXMLDocumentController.a;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;
import java.time.LocalDate;
import java.util.Collections;


/**
 *
 * @author Katie
 */
public class ITS5201FinalProject extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
       
        //Run the parent window
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
       
        
        Scene scene = new Scene(root);
      
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
         PreparedStatement stmt = null;
         Connection conn = null;
        //Connect to database
        try{
        String driver = "com.mysql.jdbc.Driver";
        Class.forName(driver).newInstance();
        
        //Connect to database
        String connURL = "jdbc:mysql://localhost/finalProject";
        String user = "root";
        String pass = "humber";
        conn = DriverManager.getConnection(connURL, user, pass);
        String sql = "TRUNCATE TABLE headaches";
        stmt = conn.prepareStatement(sql);
        int count = stmt.executeUpdate();
        Collections.sort(a.getHDateCollection());
        for (int i = 0; i < a.getHDateCollection().size(); i++){
            LocalDate date = a.getHDateCollection().get(i).getHDate();
            Date sqlDate = Date.valueOf(date);
            int severity = a.getHDateCollection().get(i).getHSeverity();
            boolean alcohol = a.getHDateCollection().get(i).isAlchol();
            boolean pms = a.getHDateCollection().get(i).isPremenstrualSymptoms();
            boolean chocolate = a.getHDateCollection().get(i).isChocolate();
            boolean weather = a.getHDateCollection().get(i).isWeatherChanges();
            boolean stress = a.getHDateCollection().get(i).isStress();
            boolean lowsleep = a.getHDateCollection().get(i).isLowSleep();
            String user1 = a.getHDateCollection().get(i).getUserTrigger1();
            String user2 = a.getHDateCollection().get(i).getUserTrigger2();
            String user3 = a.getHDateCollection().get(i).getUserTrigger3();
                       
            String sql1 = "INSERT INTO headaches VALUES(?,?,?,?,?,?,?,?,?,?,?)";
            stmt = conn.prepareStatement(sql1);
            stmt.setDate(1, sqlDate);
            stmt.setInt(2, severity);
            stmt.setBoolean(3,alcohol);
            stmt.setBoolean(4,pms);
            stmt.setBoolean(5,chocolate);
            stmt.setBoolean(6,weather);
            stmt.setBoolean(7, stress);
            stmt.setBoolean(8,lowsleep);
            stmt.setString(9,user1);
            stmt.setString(10,user2);
            stmt.setString(11,user3);
            int count1 = stmt.executeUpdate();
        }
       
        }
        catch(Exception ex){System.err.print(ex);}
        finally
        {
            try{
            conn.close();
            stmt.close();
            
            }
            catch(SQLException ex){
                System.err.print(ex);
            }
        }
        
        ArrayList<HeadacheDate> test = FXMLDocumentController.a.getHDateCollection();
        HeadacheDate[] h1 = test.toArray(new HeadacheDate[test.size()]);
        java.util.Arrays.sort(h1);
      
        
    }
    
}
