/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package its5201finalproject;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;


/**
 * FXML Controller class
 *
 * @author Katie
 */
public class AddScreenController implements Initializable {
   
    @FXML
    private GridPane grid;
    @FXML
    private DatePicker dtPick;
    
    @FXML
    private ListView<String> lstTriggers;
    String[] triggers = {"Alcohol", "Premenstrual Symptoms", "Chocolate", "Weather Changes", "Stress", "Lack of Sleep"};
    ObservableList<String> ObvTriggers = FXCollections.observableArrayList(triggers);
    
    @FXML
    private Button btnOK;
    @FXML
    private Button btnCancel;
    @FXML     
    private Button btnClear;
    
    @FXML
    private TextField txtTrigger1;
    @FXML
    private TextField txtTrigger2;
    @FXML
    private TextField txtTrigger3;
    @FXML
    private ComboBox<Number> cboSeverity;
    Number[] severity = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ObservableList<Number> lstSeverity = FXCollections.observableArrayList(severity);
       
    private LocalDate date;
    private int HSeverity=0;
    private boolean alcohol = false;
    private boolean premenstrualSymptoms = false;
    private boolean chocolate = false;
    private boolean weatherChanges = false;
    private boolean stress = false;
    private boolean lackOfSleep = false;
    HeadacheDate n = new HeadacheDate();
        public HeadacheDate getHeadacheDateFromAdd(){
            return n;
        }
        
    //Set methods initialize values if the user tries to add an entry already available    
    public void setDate(LocalDate ldt){
        dtPick.setValue(ldt);
    }    
        
    public void setSeverity(Number num){
        cboSeverity.setValue(num);
    }
    
    public void setAlcohol(boolean bool){
        if(bool)
            lstTriggers.getSelectionModel().select("Alcohol");
    }
    
    public void setChocolate(boolean bool){
        if(bool)
            lstTriggers.getSelectionModel().select("Chocolate");
    }
    
    public void setPMS(boolean bool){
        if(bool)
            lstTriggers.getSelectionModel().select("Premenstrual Symptoms");
    }
    
    public void setWeather(boolean bool){
        if(bool)
            lstTriggers.getSelectionModel().select("Weather Changes");
    }
    
    public void setStress(boolean bool){
        if(bool)
            lstTriggers.getSelectionModel().select("Stress");
    }
    
    public void setLowSleep(boolean bool){
        if(bool)
            lstTriggers.getSelectionModel().select("Lack of Sleep");
    }
    
    public void setUserTrigger1(String trigger){
        txtTrigger1.setText(trigger);
    }
    
    public void setUserTrigger2(String trigger){
        txtTrigger2.setText(trigger);
    }
    
    public void setUserTrigger3(String trigger){
        txtTrigger3.setText(trigger);
    }
    
    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
       //Disable the date picker so the user can see the date, but can't alter it. 
        //They can always press cancel!
       dtPick.setDisable(true);
       //Initialize the item list for each list or combo box
       lstTriggers.setItems(ObvTriggers);
       lstTriggers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
       cboSeverity.setItems(lstSeverity);
       //If no selection has been made for severity, take the inital value of 0
       cboSeverity.setOnAction(e->{
           if(!cboSeverity.getSelectionModel().isEmpty())
                HSeverity = (int)(cboSeverity.getSelectionModel().getSelectedItem());
          
       });
       //allow multiple selections
       lstTriggers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
       //Add a change listener to update the boolean values for triggers when selected
       lstTriggers.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<String>() {

      @Override
      public void onChanged(Change<? extends String> c) {
         //sets the booleans for the triggers to true/false depending on which items are selected
          while(c.next()){
                if (lstTriggers.getSelectionModel().isSelected(0)) 
                    alcohol = true;
                else if (!lstTriggers.getSelectionModel().isSelected(0))
                    alcohol = false;
                if (lstTriggers.getSelectionModel().isSelected(1))
                    premenstrualSymptoms = true;
                else if (!lstTriggers.getSelectionModel().isSelected(1)) 
                    premenstrualSymptoms = false;
                if (lstTriggers.getSelectionModel().isSelected(2))
                    chocolate = true;
                else if (!lstTriggers.getSelectionModel().isSelected(2))
                    chocolate = false;
                if (lstTriggers.getSelectionModel().isSelected(3))
                    weatherChanges = true;
                else if (!lstTriggers.getSelectionModel().isSelected(3)) 
                    weatherChanges = false;
                if (lstTriggers.getSelectionModel().isSelected(4))
                    stress = true;
                else if (!lstTriggers.getSelectionModel().isSelected(4)) 
                    stress = false;
                if (lstTriggers.getSelectionModel().isSelected(5))
                    lackOfSleep = true;
                else if (!lstTriggers.getSelectionModel().isSelected(5)) 
                    lackOfSleep = false;
            }
      }
        });
       //If the user presses clear, return all values to default and remove the record from the list
       btnClear.setOnAction(e->{
           //Prompt the user - are you sure?
           int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear everything? This will delete all your information!",
           "Clear Record?", JOptionPane.OK_CANCEL_OPTION);
            if(reply == JOptionPane.OK_OPTION){
            //set values to defaults
           cboSeverity.setValue(0);
           lstTriggers.getSelectionModel().clearSelection();
           txtTrigger1.setText("");
           txtTrigger2.setText("");
           txtTrigger3.setText("");
           //Delete the record from the list
           for(int i = 0; i < FXMLDocumentController.a.getHDateCollection().size(); i++){
               if(FXMLDocumentController.a.getHDateCollection().get(i).getHDate().equals(dtPick.getValue()))
                        FXMLDocumentController.a.getHDateCollection().remove(FXMLDocumentController.a.getHDateCollection().get(i));
                   }
           }
          
       });
       
       //Close the window if the user selects cancel
       btnCancel.setOnAction(e->{
           //Obtained this line of code from stack overflow
           ((Node)(e.getSource())).getScene().getWindow().hide();
       });
       //If the user presses okay, create a new headacheDate object with the user selected properties and add it the list
        btnOK.setOnAction(e->{
           
           //ArrayList<HeadacheDate> hList = FXMLDocumentController.a.getHDateCollection();
                
        //Search through the list. If the date is already there, remove the old value         
                for (int i = 0; i < FXMLDocumentController.a.getHDateCollection().size() ; i++){
                    if  (FXMLDocumentController.a.getHDateCollection().get(i).getHDate().equals(dtPick.getValue())){
                        
                        FXMLDocumentController.a.getHDateCollection().remove(FXMLDocumentController.a.getHDateCollection().get(i));
                    }
                }
            
           n.setHDate(dtPick.getValue());
           n.setHSeverity((int)cboSeverity.getValue());
           n.setAlcohol(alcohol);
           n.setPremenstrualSymptoms(premenstrualSymptoms);
           n.setChocolate(chocolate);
           n.setWeatherChanges(weatherChanges);
           n.setStress(stress);
           n.setLowSleep(lackOfSleep);
           
           n.setUserTrigger1(txtTrigger1.getText());
           n.setUserTrigger2(txtTrigger2.getText());
           n.setUserTrigger3(txtTrigger3.getText());
           FXMLDocumentController.a.getHDateCollection().add(n);
           //Close the window
           ((Node)(e.getSource())).getScene().getWindow().hide();

           
        });
        
       
       
        
    }    
    
}
