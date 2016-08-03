/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package its5201finalproject;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventType;


/**
 * FXML Controller class
 *
 * @author Katie
 */
public class FXMLDocumentController implements Initializable {
    //Declare all FXML controls
    @FXML
    private DatePicker dtePicker;
    
    @FXML
    private ComboBox<String> cboSummary;
    String[] months = { "September", "October", "Novemeber", "December", "January", "February", "March",
    "April", "May", "June", "July", "August"};
    ObservableList<String> lstMonths = FXCollections.observableArrayList(months);
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnMonth;
        


    
   
    
    /**
     * This method returns the value of the date picker object
     * If the user doesn't enter a date it defaults to the current date
     * @return the value of the date picker
     */
    public LocalDate getUserDate(){
        if(dtePicker.getValue() == null)
            return LocalDate.now();
               
        return dtePicker.getValue();
    }
    //Create a static instance of the headache date collection. This will be used during the entire run of the program
    public static HeadacheDateCollection a = new HeadacheDateCollection();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        //Connect to database
        try{
        String driver = "com.mysql.jdbc.Driver";
        Class.forName(driver).newInstance();
        
        //Connect to database
        String connURL = "jdbc:mysql://localhost/finalProject";
        String user = "root";
        String pass = "humber";
        conn = DriverManager.getConnection(connURL, user, pass);
        String sql = "SELECT * FROM headaches";
        stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();
        while(rs.next()){
            LocalDate date = rs.getDate(1).toLocalDate();
            int severity = rs.getInt(2);
            boolean alcohol = rs.getBoolean(3);
            boolean pms = rs.getBoolean(4);
            boolean chocolate = rs.getBoolean(5);
            boolean weather = rs.getBoolean(6);
            boolean stress = rs.getBoolean(7);
            boolean lowsleep = rs.getBoolean(8);
            String user1 = rs.getString(9);
            String user2 = rs.getString(10);
            String user3 = rs.getString(11);
           
            HeadacheDate test = new HeadacheDate();
            test.setHDate(date);
            test.setHSeverity(severity);
            test.setAlcohol(alcohol);
            test.setPremenstrualSymptoms(pms);
            test.setChocolate(chocolate);
            test.setWeatherChanges(weather);
            test.setStress(stress);
            test.setLowSleep(lowsleep);
            test.setUserTrigger1(user1);
            test.setUserTrigger2(user2);
            test.setUserTrigger3(user3);
            
            a.getHDateCollection().add(test);
            
        }
       
       
        }
        catch(Exception ex){System.err.print(ex);}
        finally
        {
            try{
            rs.close();
            
            }
            catch(SQLException ex){
                System.err.print(ex);
            }
        }
        
        //Initialize the date to today
        dtePicker.setValue(LocalDate.now());
        dtePicker.setOnAction(e->{
             if (dtePicker.getValue().isAfter(LocalDate.now())){
               
                 dtePicker.setValue(LocalDate.now());
                 dtePicker.hide();
            JOptionPane.showMessageDialog(null, "You cannot enter a headache in the future",
           "Time-travelling not allowed", JOptionPane.ERROR_MESSAGE);
            
        }
        });
        
        //Set the items of the combo box to the months
        cboSummary.setItems(lstMonths);

        
        //Displays the add screen when the add button is clicked
        btnAdd.setOnAction (e->{
            try {
                
                Stage addStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddScreen.fxml"));
                Parent root = (Parent)loader.load();
                Scene addScene = new Scene(root);
                
                addStage.setScene(addScene);
                
                
                //Creates an instance of the AddScreenController              
                AddScreenController addController = loader.<AddScreenController>getController();
               
                //Search to see if the list already includes the date. If found, all controls will be initialized with previous values
                ArrayList<HeadacheDate> hList = FXMLDocumentController.a.getHDateCollection();
                HeadacheDate[] hArr = hList.toArray(new HeadacheDate[hList.size()]);
                boolean found = false;
                for (int i = 0; i < hArr.length; i++) {
                    if (hArr[i].getHDate().equals(getUserDate())) {
                        found = true;
                    addController.setDate(getUserDate());
                    addController.setSeverity(hArr[i].getHSeverity());
                    addController.setAlcohol(hArr[i].isAlchol());
                    addController.setChocolate(hArr[i].isChocolate());
                    addController.setPMS(hArr[i].isPremenstrualSymptoms());
                    addController.setWeather(hArr[i].isWeatherChanges());
                    addController.setStress(hArr[i].isStress());
                    addController.setLowSleep(hArr[i].isLowSleep());
                    addController.setUserTrigger1(hArr[i].getUserTrigger1());
                    addController.setUserTrigger2(hArr[i].getUserTrigger2());
                    addController.setUserTrigger3(hArr[i].getUserTrigger3());
                }
                }
                //If the date is not already in the list, initialize the date to the entered date and the severity to 0.
                 if (!found){                                     
                    addController.setDate(getUserDate());
                    addController.setSeverity(0);
                 }
                addStage.show();
               
            }
            catch(Exception ex){
                System.out.println(ex);
            }
            });
        
        //Displays the search screen when the search button is clicked
        
        btnSearch.setOnAction (e->{
            try {
                
                Stage searchStage = new Stage();
                Scene searchScene = new Scene(FXMLLoader.load(getClass().getResource("SearchScreen.fxml")));
                searchStage.setScene(searchScene);
                searchStage.show();
            }
            catch(Exception ex){
                System.out.println(ex);
            }
            });
        
        //Calculates and displays a monthly summary for the month selected by the user
        btnMonth.setOnAction(e->{
           //Run the month dates method to make a list of headache dates for the selected month
            ArrayList<HeadacheDate> nArray = FXMLDocumentController.a.monthDates(cboSummary.getValue());
            //System.out.print(cboSummary.getValue());
            //Calls the average method for the HeadacheDateCollection instance a
            double average = FXMLDocumentController.a.average(cboSummary.getValue());
            //Makes an array for easier iteration
            HeadacheDate[] hArray = nArray.toArray(new HeadacheDate[nArray.size()]);
            //Initializes counter objects to 0
            int headacheCounter = 0;
            int alcoholCounter = 0;
            int chocolateCounter = 0;
            int PMSCounter = 0;
            int weatherCounter =0;
            int stressCounter = 0;
            int lowSleepCounter = 0;
            //Create a list that can change in length to record all user-entered triggers
            ArrayList<String> otherEntries = new ArrayList<String>(); 
            //Iterate through each headache date object in the month array. Increment each counter if the trigger is true
            for(int i = 0; i < hArray.length; i++){
                //Only count the days where the severity is greater than 0
                if( hArray[i].getHSeverity() > 0) {
                    headacheCounter++;
                if (hArray[i].isAlchol())
                    alcoholCounter++;
                if(hArray[i].isChocolate())
                    chocolateCounter++;
                if(hArray[i].isLowSleep())
                    lowSleepCounter++;
                if(hArray[i].isPremenstrualSymptoms())
                    PMSCounter++;
                if(hArray[i].isWeatherChanges())
                    weatherCounter++;
                if(hArray[i].isStress())
                    stressCounter++;
                //Add the user triggers to the list, only if they are not already there
                if(!hArray[i].getUserTrigger1().equals("")){
                    if(!otherEntries.contains(hArray[i].getUserTrigger1()))
                        otherEntries.add(hArray[i].getUserTrigger1());                                     
                }
                if(!hArray[i].getUserTrigger2().equals("")) {
                    if(!otherEntries.contains(hArray[i].getUserTrigger2()))
                        otherEntries.add(hArray[i].getUserTrigger2());                                     
                }
                   
                if(!hArray[i].getUserTrigger3().equals("")) {
                    if(!otherEntries.contains(hArray[i].getUserTrigger3()))
                        otherEntries.add(hArray[i].getUserTrigger3());                                     
                }
                }     
            }
         
          //Create a string object to store the list of other entries in a message that will be displayed  
          /* In the future, this will be further developed to  allow for tracking how many times the user-entered
            triggers occur. But for now, this program is only capable of listing the user triggers once each*/  
          String[]  otherEntryArray = otherEntries.toArray(new String[otherEntries.size()]);
          String    otherEntriesMessage="Other triggers: \n";
          for(int i = 0; i< otherEntryArray.length; i++){
              otherEntriesMessage += otherEntryArray[i] + "\n";
          }
            //If there are days recorded, display a message to the user with the number of headache days, average severity, and number of triggers
            if((nArray.size() > 0)){
            JOptionPane.showMessageDialog(null,"Number of Headache days: " + headacheCounter + "\n" + 
                "Average Severity: " + average + "\nTriggers: \n" + "Headache days including alcohol:" + alcoholCounter + "\n"
                +"Headache days including premenstrual symptoms:" +  PMSCounter +"\n" + "Headache days including chocolate:" + chocolateCounter + "\n"
                + "Headache days including weather changes:" + weatherCounter + "\n" + "Headache days including stress:" +stressCounter+  "\n"
                + "Headache days including lack of sleep:" + lowSleepCounter + "\n" + otherEntriesMessage, "Summary", 
                JOptionPane.PLAIN_MESSAGE);
            }
            //If there are no dates in the month, print the following message
            else {
                JOptionPane.showMessageDialog(null,"No data for selected month", "Summary", 
                JOptionPane.PLAIN_MESSAGE);
            
            }
            
        });
        
        
        
    }    
    
}
