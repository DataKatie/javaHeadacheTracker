/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package its5201finalproject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

/**
 *
 * @author Katie
 */
public class HeadacheDateCollection {
    /**
     * The constructor creates a new headache date collection each time this class is instantiated.
     * Note that for this project, only one instance is required.
     */
    HeadacheDateCollection(){
        hDateCollection = new ArrayList<HeadacheDate>();
    }
    
    private ArrayList<HeadacheDate> hDateCollection;
    /**
     * This is the accessor for the headache date collection
     * @return the headache date collection 
     */
    public ArrayList<HeadacheDate> getHDateCollection(){
        return hDateCollection;
    }
    
    /**
     * The last10days method is used to make a collection of headache dates that occurred in the last 
     * 10 days only. It will be used to filter search results
     * @return an ArrayList of headache dates during the last 10 days only
     */
    
    public ArrayList<HeadacheDate> last10days(){
        
        LocalDate tenBefore = LocalDate.now();
        tenBefore= tenBefore.minusDays(10);
        HeadacheDate[] hArr = hDateCollection.toArray(new HeadacheDate[hDateCollection.size()]);
        ArrayList<HeadacheDate> tenDayCollection = new ArrayList<HeadacheDate>();
        for (int i = 0; i < hArr.length; i++) {
            if(!((hArr[i]).getHDate().isBefore(tenBefore)))
                tenDayCollection.add(hArr[i]);
        }
        return tenDayCollection;
    }
    
    /**
     * The last30days method is used to make a collection of headache dates that occurred in the last 
     * 30 days only. It will be used to filter search results
     * @return an ArrayList of headache dates during the last 30 days only
     */
    
    public ArrayList<HeadacheDate> last30days(){
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
    
    /**
     * The monthDates method collects the headaches entered by the user and returns a collection of the headaches
     * only in the month specified
     * @param month is the month you are collecting over as a string
     * @return an array list of headache dates for the specific month 
     */
    
    public ArrayList<HeadacheDate> monthDates (String month){
        
        int numMonth = -1;
        String[] monthArray = {"January", "February", "March", "April", "May", "June", "July", "August",
        "September", "October", "November", "December"};
        for(int i = 0; i < 12; i++){
            if(month.equals(monthArray[i])) {
                numMonth = i;
                break;
            }
        }
        
        HeadacheDate[] hArr = this.getHDateCollection().toArray(new HeadacheDate[hDateCollection.size()]);
        ArrayList<HeadacheDate> monthCollection = new ArrayList<HeadacheDate>();
        for (int i = 0; i < hArr.length; i++) {
            
            if(((hArr[i]).getHDate().getMonthValue() -1 == numMonth)){
                monthCollection.add(hArr[i]);
                //System.out.print("value added");
            }
        }
        return monthCollection;
    }
    
    /**
     * The average method calculates the average headache severity based on
     * the intensity entered and the number of entries
     * @param month is the month you are averaging over
     * @return the average value
     */
    
    public double average(String month){
    double average = 0; 
    ArrayList<HeadacheDate> monthList = this.monthDates(month);
    monthList.trimToSize();
    
        int count = 0;
        int sum = 0;
        for (int i = 0; i<monthList.size(); i++){
            
            if(monthList.get(i).getHSeverity() > 0) {
                count ++;
                sum += monthList.get(i).getHSeverity();
             
            }
            if (count > 0)
                average =  (double)sum/(double)count;
            
        
    }
        return average;
    }
    
   /* public ArrayList<HeadacheDate> sortArray(ArrayList<HeadacheDate> hArrList){
       HeadacheDate[] hArr = hArrList.toArray(new HeadacheDate[hArrList.size()]);
       java.util.Arrays.sort(hArr);
       hArrList.clear();
       for(int i = 0; i < hArr.length; i++) {
           hArrList.add(hArr[i]);
       }
       
       return hArrList;
    } */
}
