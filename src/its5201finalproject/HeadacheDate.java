/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package its5201finalproject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

/**
 *
 * @author Katie
 */
public class HeadacheDate implements Comparable {
    //All class variables
    private LocalDate hDate;
    private int hSeverity;
    private boolean alcohol; 
    private boolean premenstrualSymptoms; 
    private boolean chocolate; 
    private boolean weatherChanges;
    private boolean stress;
    private boolean lowSleep; 
    private String userTrigger1;	
    private String userTrigger2;	
    private String userTrigger3;	

    
    //Get and Set methods for all variables
    public LocalDate getHDate(){
        return hDate;
    }
    
    public void setHDate(LocalDate hDate){
        this.hDate = hDate;
    }
    
    public int getHSeverity(){
        return hSeverity;
    }
    
    public void setHSeverity(int hSeverity){
        this.hSeverity = hSeverity;
    }
    
   public boolean isAlchol(){
        return alcohol;
    }
    
    public void setAlcohol(boolean alcohol){
        this.alcohol = alcohol;
    } 
    
    public boolean isPremenstrualSymptoms(){
        return premenstrualSymptoms;
    }
    
    public void setPremenstrualSymptoms(boolean premenstrualSymptoms){
        this.premenstrualSymptoms = premenstrualSymptoms;
    } 
    
    public boolean isChocolate(){
        return chocolate;
    }
    
    public void setChocolate(boolean chocolate){
        this.chocolate = chocolate;
    } 
    
    public boolean isWeatherChanges(){
        return weatherChanges;
    }
    
    public void setWeatherChanges(boolean weatherChanges){
        this.weatherChanges = weatherChanges;
    } 
    
    public boolean isStress(){
        return stress;
    }
    
    public void setStress(boolean stress){
        this.stress = stress;
    } 
    
    public boolean isLowSleep(){
        return lowSleep;
    }
    
    public void setLowSleep(boolean lowSleep){
        this.lowSleep = lowSleep;
    } 
    
    public String getUserTrigger1(){
        return userTrigger1;
    }
    
    public void setUserTrigger1(String userTrigger1){
        this.userTrigger1 = userTrigger1;
    } 
    
    public String getUserTrigger2(){
        return userTrigger2;
    }
    
    public void setUserTrigger2(String userTrigger2){
        this.userTrigger2 = userTrigger2;
    } 
    
    public String getUserTrigger3(){
        return userTrigger3;
    }
    
    public void setUserTrigger3(String userTrigger3){
        this.userTrigger3 = userTrigger3;
    } 
    
    /**
     * This method has an override so that a collection of Headache dates can
     * be sorted by date
     * @param t is an object which will be cast to HeadacheDate type, which is
     * being compared
     * @return an integer to represent the outcome of the comparison: -1 for smaller
     * 0 for the same size, and 1 for bigger. Note that this application will never
     * allow two headache dates with the same date to be entered to allow perfect sorting
     */
    @Override
    public int compareTo(Object t) {
        HeadacheDate o = (HeadacheDate)t;
        if ((this.hDate).isBefore(o.hDate))
            return -1;
        else  if (this.hDate.equals(o.getHDate()))
            return 0;
        else 
            return 1;
    }
    
    /*
    
    @Override
    public boolean equals(Object t){
        HeadacheDate o = (HeadacheDate)t;
        return (this.hDate.equals(o.hDate));
            
    }
*/
}
