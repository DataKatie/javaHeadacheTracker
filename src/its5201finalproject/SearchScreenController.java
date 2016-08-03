/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package its5201finalproject;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Katie
 */
public class SearchScreenController implements Initializable {
    @FXML
    private ComboBox<Number> cboSeverity;
    Number[] severity = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ObservableList<Number> lstSeverity = FXCollections.observableArrayList(severity);
    @FXML
    private Button btnSeverity;
    @FXML
    private ComboBox<String> cboTrigger;
    String[] triggers = {"Alcohol", "Premenstrual Symptoms", "Chocolate", "Weather Changes", "Stress", "Lack of Sleep", "Enter your own"};
    ObservableList<String> ObvTriggers = FXCollections.observableArrayList(triggers);@FXML
    private Button btnTrigger;
    
    @FXML
    private Button btnCancel;
    
    @FXML
    private ComboBox<String> cboFilter;
    String[] filters = {"All", "Last 10 days", "Last 30 days"};
    ObservableList<String> lstFilters = FXCollections.observableArrayList(filters);@FXML

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Initialize items for combo boxes and set the trigger as editable
        cboTrigger.setItems(ObvTriggers);
        cboSeverity.setItems(lstSeverity);
        cboTrigger.setEditable(true);
        cboFilter.setItems(lstFilters);
        
        //Initialize values for combo boxes
        cboFilter.setValue("All");
        cboTrigger.setValue("Enter your own");
        cboSeverity.setValue(0);
       
        
        //if the user sets their own value for triggers, the listener will set this
        cboTrigger.getEditor().textProperty().addListener(new ChangeListener<String>() {
                @Override public void changed(ObservableValue ov, String t, String t1) {
                    
                    cboTrigger.setValue(t1);
                    }  
                
               
                });
      
        /*
        On pressing the severity button the program will search for headaches and return a list of headaches
        that have a severity greater or equal to the one indicated by the user.
                */
        btnSeverity.setOnAction(e ->{
            
            ArrayList<HeadacheDate> nArray = FXMLDocumentController.a.getHDateCollection();
            //Check if filters have been applied and adjust if so
            if((cboFilter.getValue().equals("All")) || (cboFilter.getValue() == null))
                nArray = FXMLDocumentController.a.getHDateCollection();
            else if (cboFilter.getValue().equals("Last 10 days")) {
            nArray = FXMLDocumentController.a.last10days();
            
            }
            else if (cboFilter.getValue().equals("Last 30 days")){
            nArray = FXMLDocumentController.a.last30days();      
            
        
        }
            //Make the headache ArrayList into an array and sort by date
            HeadacheDate[] hDates = nArray.toArray(new HeadacheDate[nArray.size()]);
            Arrays.sort(hDates);
            
            String message = "";
           for (int i = 0; i < hDates.length; i++ ) {
                
                if (hDates[i].getHSeverity() >= (int)cboSeverity.getValue()){
                   //Check which triggers are true and add to the list of triggers
                    String triggersList = "";
                    if (hDates[i].isAlchol())
                        triggersList += "alcohol, ";
                    if (hDates[i].isChocolate())
                        triggersList += "chocolate, ";
                    if (hDates[i].isPremenstrualSymptoms())
                        triggersList += "premenstrual symptoms, ";
                    if (hDates[i].isWeatherChanges())
                        triggersList += "weather changes, ";
                    if (hDates[i].isStress())
                        triggersList += "stress, ";
                    if(hDates[i].isLowSleep())
                        triggersList += "low sleep ";
                    //add user triggers
                    triggersList += hDates[i].getUserTrigger1() + " " + hDates[i].getUserTrigger2() + " "  + hDates[i].getUserTrigger3();
                    //Add the line showing the date, severity and a list of triggers
                     message += hDates[i].getHDate().getMonth() +" " + hDates[i].getHDate().getDayOfMonth() + " " + 
                hDates[i].getHDate().getYear() + "      \t"  + hDates[i].getHSeverity() + "     \t" + triggersList +   " \n" ;
                    
                } 
        
            } 
           //Display an error message if no results were found
           if(message.equals(""))
                    message = "no triggers found in date range";
           //Display the message in a pane for the user to see
            JOptionPane.showMessageDialog(null,"    Date    \t          Severity     \t Triggers \n\n" + 
                message +   " \n" , "Search Result", 
                JOptionPane.PLAIN_MESSAGE);
            //Close the search window (Obtained from stack Overflow)
            ((Node)(e.getSource())).getScene().getWindow().hide();
        });
        
        /* If the user clicks the button to search by trigger, the program will first locate which trigger was selected
        including the user-set triggers. Then it will perform the same actions as above, creating a list
        for the user      
        */
        btnTrigger.setOnAction(e->{
            ArrayList<HeadacheDate> nArray = FXMLDocumentController.a.getHDateCollection();
            
            //Check if filters were applied 
            if((cboFilter.getValue().equals("All")) || (cboFilter.getValue() == null))
                nArray = FXMLDocumentController.a.getHDateCollection();
            else if (cboFilter.getValue().equals("Last 10 days")) {
            nArray = FXMLDocumentController.a.last10days();
            
            }
            else if (cboFilter.getValue().equals("Last 30 days")){
            nArray = FXMLDocumentController.a.last30days();      
             }
            
            
            HeadacheDate[] hDates = nArray.toArray(new HeadacheDate[nArray.size()]);
            Arrays.sort(hDates);
            //Initalize the empty message
            String message = "";
            
            
             for (int i = 0; i < hDates.length; i++ ) {
                 //If the trigger selected is alcohol, run this code
                 if((cboTrigger.getValue()).equals("Alcohol")){
                      if (hDates[i].isAlchol()){
                   
                    String triggersList = "";
                    if (hDates[i].isAlchol())
                        triggersList += "alcohol, ";
                    if (hDates[i].isChocolate())
                        triggersList += "chocolate, ";
                    if (hDates[i].isPremenstrualSymptoms())
                        triggersList += "premenstrual symptoms, ";
                    if (hDates[i].isWeatherChanges())
                        triggersList += "weather changes, ";
                    if (hDates[i].isStress())
                        triggersList += "stress, ";
                    if(hDates[i].isLowSleep())
                        triggersList += "low sleep ";
                    triggersList += hDates[i].getUserTrigger1() + " " + hDates[i].getUserTrigger2() + " "  + hDates[i].getUserTrigger3();
                    
                     message += hDates[i].getHDate().getMonth() +" " + hDates[i].getHDate().getDayOfMonth() + " " + 
                hDates[i].getHDate().getYear() + "      \t"  + hDates[i].getHSeverity() + "     \t" + triggersList +   " \n" ;
                    
                }
                                  
             }
             //If the trigger selected is chocolate, run this code    
             else if ((cboTrigger.getValue()).equals("Chocolate")){
                      if (hDates[i].isChocolate()){
                   
                    String triggersList = "";
                    if (hDates[i].isAlchol())
                        triggersList += "alcohol, ";
                    if (hDates[i].isChocolate())
                        triggersList += "chocolate, ";
                    if (hDates[i].isPremenstrualSymptoms())
                        triggersList += "premenstrual symptoms, ";
                    if (hDates[i].isWeatherChanges())
                        triggersList += "weather changes, ";
                    if (hDates[i].isStress())
                        triggersList += "stress, ";
                    if(hDates[i].isLowSleep())
                        triggersList += "low sleep ";
                    triggersList += hDates[i].getUserTrigger1() + " " + hDates[i].getUserTrigger2() + " "  + hDates[i].getUserTrigger3();
                    
                     message += hDates[i].getHDate().getMonth() +" " + hDates[i].getHDate().getDayOfMonth() + " " + 
                hDates[i].getHDate().getYear() + "      \t"  + hDates[i].getHSeverity() + "     \t" + triggersList +   " \n" ;
                    
                }
             }
                 //If the user selected premenstrual symptoms, run this code
                 else if ((cboTrigger.getValue()).equals("Premenstrual Symptoms")){
                      if (hDates[i].isPremenstrualSymptoms()){
                   
                    String triggersList = "";
                    if (hDates[i].isAlchol())
                        triggersList += "alcohol, ";
                    if (hDates[i].isChocolate())
                        triggersList += "chocolate, ";
                    if (hDates[i].isPremenstrualSymptoms())
                        triggersList += "premenstrual symptoms, ";
                    if (hDates[i].isWeatherChanges())
                        triggersList += "weather changes, ";
                    if (hDates[i].isStress())
                        triggersList += "stress, ";
                    if(hDates[i].isLowSleep())
                        triggersList += "low sleep ";
                    triggersList += hDates[i].getUserTrigger1() + " " + hDates[i].getUserTrigger2() + " "  + hDates[i].getUserTrigger3();
                    
                     message += hDates[i].getHDate().getMonth() +" " + hDates[i].getHDate().getDayOfMonth() + " " + 
                hDates[i].getHDate().getYear() + "      \t"  + hDates[i].getHSeverity() + "     \t" + triggersList +   " \n" ;
                    
                }
             }
              //If the user selected weather changes, run this code  
                else if ((cboTrigger.getValue()).equals("Weather Changes")){
                      if (hDates[i].isWeatherChanges()){
                   
                    String triggersList = "";
                    if (hDates[i].isAlchol())
                        triggersList += "alcohol, ";
                    if (hDates[i].isChocolate())
                        triggersList += "chocolate, ";
                    if (hDates[i].isPremenstrualSymptoms())
                        triggersList += "premenstrual symptoms, ";
                    if (hDates[i].isWeatherChanges())
                        triggersList += "weather changes, ";
                    if (hDates[i].isStress())
                        triggersList += "stress, ";
                    if(hDates[i].isLowSleep())
                        triggersList += "low sleep ";
                    triggersList += hDates[i].getUserTrigger1() + " " + hDates[i].getUserTrigger2() + " "  + hDates[i].getUserTrigger3();
                    
                     message += hDates[i].getHDate().getMonth() +" " + hDates[i].getHDate().getDayOfMonth() + " " + 
                hDates[i].getHDate().getYear() + "      \t"  + hDates[i].getHSeverity() + "     \t" + triggersList +   " \n" ;
                    
                }
             }   
             //If the user selected stress, run this code    
            else if ((cboTrigger.getValue()).equals("Stress")){
                      if (hDates[i].isStress()){
                   
                    String triggersList = "";
                    if (hDates[i].isAlchol())
                        triggersList += "alcohol, ";
                    if (hDates[i].isChocolate())
                        triggersList += "chocolate, ";
                    if (hDates[i].isPremenstrualSymptoms())
                        triggersList += "premenstrual symptoms, ";
                    if (hDates[i].isWeatherChanges())
                        triggersList += "weather changes, ";
                    if (hDates[i].isStress())
                        triggersList += "stress, ";
                    if(hDates[i].isLowSleep())
                        triggersList += "low sleep ";
                    triggersList += hDates[i].getUserTrigger1() + " " + hDates[i].getUserTrigger2() + " "  + hDates[i].getUserTrigger3();
                    
                     message += hDates[i].getHDate().getMonth() +" " + hDates[i].getHDate().getDayOfMonth() + " " + 
                hDates[i].getHDate().getYear() + "      \t"  + hDates[i].getHSeverity() + "     \t" + triggersList +   " \n" ;
                    
                }
             } 
            //If the user selected lack of sleep, run this code
             else if ((cboTrigger.getValue()).equals("Lack of Sleep")){
                      if (hDates[i].isLowSleep()){
                   
                    String triggersList = "";
                    if (hDates[i].isAlchol())
                        triggersList += "alcohol, ";
                    if (hDates[i].isChocolate())
                        triggersList += "chocolate, ";
                    if (hDates[i].isPremenstrualSymptoms())
                        triggersList += "premenstrual symptoms, ";
                    if (hDates[i].isWeatherChanges())
                        triggersList += "weather changes, ";
                    if (hDates[i].isStress())
                        triggersList += "stress, ";
                    if(hDates[i].isLowSleep())
                        triggersList += "low sleep ";
                    triggersList += hDates[i].getUserTrigger1() + " " + hDates[i].getUserTrigger2() + " "  + hDates[i].getUserTrigger3();
                    
                     message += hDates[i].getHDate().getMonth() +" " + hDates[i].getHDate().getDayOfMonth() + " " + 
                hDates[i].getHDate().getYear() + "      \t"  + hDates[i].getHSeverity() + "     \t" + triggersList +   " \n" ;
                    
                }
             } 
            
             //If the user entered their own value, run this code
            else { 
               
               //If the value entered matches the value of any of the three user triggers, then add an entry to the list
                    
                if (hDates[i].getUserTrigger1().equals(cboTrigger.getValue()) || hDates[i].getUserTrigger2().equals(cboTrigger.getValue())||
                        hDates[i].getUserTrigger3().equals(cboTrigger.getValue())){
                   
                    String triggersList = "";
                    if (hDates[i].isAlchol())
                        triggersList += "alcohol, ";
                    if (hDates[i].isChocolate())
                        triggersList += "chocolate, ";
                    if (hDates[i].isPremenstrualSymptoms())
                        triggersList += "premenstrual symptoms, ";
                    if (hDates[i].isWeatherChanges())
                        triggersList += "weather changes, ";
                    if (hDates[i].isStress())
                        triggersList += "stress, ";
                    if(hDates[i].isLowSleep())
                        triggersList += "low sleep ";
                    triggersList += hDates[i].getUserTrigger1() + " " + hDates[i].getUserTrigger2() + " "  + hDates[i].getUserTrigger3();
                    
                     message += hDates[i].getHDate().getMonth() +" " + hDates[i].getHDate().getDayOfMonth() + " " + 
                hDates[i].getHDate().getYear() + "      \t"  + hDates[i].getHSeverity() + "     \t" + triggersList +   " \n" ;
                    
                }
                
                
        
            } 
            
           }   
                //If no dates matching the criteria were found, give the user an error message
                if(message.equals(""))
                    message = "no triggers found in date range";
                //Display the list to the user
                JOptionPane.showMessageDialog(null,"    Date    \t          Severity     \t Triggers \n\n" + 
                message +   " \n" , "Search Result", 
                JOptionPane.PLAIN_MESSAGE);
           //Close the window 
          ((Node)(e.getSource())).getScene().getWindow().hide();
           
        });
              
        btnCancel.setOnAction(e->{
            //Close the window
            ((Node)(e.getSource())).getScene().getWindow().hide();
       });
        
    }    
    //Fiter by last 10 days
    public ArrayList<HeadacheDate> last10days(ArrayList<HeadacheDate> hDateCollection){
        
        LocalDate tenBefore = LocalDate.now();
        tenBefore= tenBefore.minusDays(10);
        System.out.print("ten before is " + tenBefore.getDayOfMonth() + " " +tenBefore.getMonth());
        HeadacheDate[] hArr = hDateCollection.toArray(new HeadacheDate[hDateCollection.size()]);
        ArrayList<HeadacheDate> tenDayCollection = new ArrayList<HeadacheDate>();
        for (int i = 0; i < hArr.length; i++) {
            if(!((hArr[i]).getHDate().isBefore(tenBefore)))
                tenDayCollection.add(hArr[i]);
        }
        return tenDayCollection;
    }
    //Filter by last 30 days
    public ArrayList<HeadacheDate> last30days(ArrayList<HeadacheDate> hDateCollection){
        LocalDate thirtyBefore = LocalDate.now(); 
        thirtyBefore = thirtyBefore.minusDays(30);
        HeadacheDate[] hArr = hDateCollection.toArray(new HeadacheDate[hDateCollection.size()]);
        ArrayList<HeadacheDate> thirtyDayCollection = new ArrayList<HeadacheDate>();
        for (int i = 0; i < hArr.length; i++) {
            if(!((hArr[i]).getHDate().isBefore(thirtyBefore)))
                thirtyDayCollection.add(hArr[i]);
        }
        return thirtyDayCollection;
    }
    
   
   
}
