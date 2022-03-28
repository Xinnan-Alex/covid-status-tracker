import java.time.*;
import java.time.format.*;

/** 
 * CustomerVisitHisotry class is to process the customer's visit history data so that It can be used in the program for other purpose.
 * A CustomerVisitHistory object should contain the following private members:
 * String customer_name - The name of the customer of the visit history,
 * Localdatetime visitdatetime - The date and time of the visit history,
 * Shop - The shop name of the visit history
 * 
 *
 * **formatter is a DateTimeFormatter object with the DateTimeFomart of ("yyyy-MM-dd HH:mm:ss") so that the date and time will be in this format.
*/
public class CustomerVisitHistory {
    //Private Members
    private String customer_name;
    private LocalDateTime visitdatetime;
    private String shop;
    private String date;
    private String time;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private DateTimeFormatter timeformat = DateTimeFormatter.ofPattern("HH:mm:ss");

    //Methods

    /**
     * Default Constructor
    */
    public CustomerVisitHistory() {}

    /**
     * Constructor
     * @param customer_name the customer's fullname for that specific visit history
     * @param shop the shop's name for that specific visit history
     * @param visitdatetime the date and time for that specific visit history
    */
    public CustomerVisitHistory(String customer_name, String shop, LocalDateTime visitdatetime) {
        this.customer_name = customer_name;
        this.visitdatetime = visitdatetime;
        this.shop = shop;
        this.date = visitdatetime.format(dateformat);
        this.time = visitdatetime.format(timeformat);
    }

    /**
     * Method of converting CustomerVisitHistory Object into String for debugging purpose in console
     * @return customer_name shop visitdatetime(yyyy-MM-dd HH:mm:ss)
    */
    public String toString() {
        return customer_name + " "  + shop + " " + visitdatetime.format(formatter);
    }

    /**
     * Method of converting CustomerVisitHisotry Objecy into CSV format for easier imports into CSV file
     * @return customer_name,shop,visitdatetime(yyyy-MM-dd HH:mm:ss)
    */
    public String toCSVString() {
        return customer_name + "," + shop + "," + visitdatetime.format(formatter);
    }

    //Getter
    /**
     * Method of getting customer's name
     * @return the customer's fullname of a specific visit history
    */ 
    public String getCustomer_name(){
        return customer_name;
    }

    /**
     * Method of getting customer's check-in Shop
     * @return the shop's name of a specific visit history
    */ 
    public String getShop(){
        return shop;
    }

    /**
     * Method of getting customer's check-in DateTime
     * @return the date and time of a specific visit history
    */ 
    public LocalDateTime getvisitdatetime(){
        return visitdatetime;
    }
    
    /**
     * Method of getting customer's check-in Date
     * @return the date of a specific visit history
     */
    public String getDate(){
        return date;
    }

    /**
     * Method of getting customer's check-in Time
     * @return the time of a specific visit history
     */
    public String getTime(){
        return time;
    }

    //Setter
    /**
     * Method of setting customer's name
     * @param  customer_name the customer's name you wish to set into a specific visit history
    */ 
    public void setcustomer_name(String customer_name){
        this.customer_name = customer_name;
    }

    /**
     * Method of setting customer's check-in DateTime
     * @param visitdatetime the date and time you wish to set into a specific visit history
    */ 
    public void setcustomer_date(LocalDateTime visitdatetime){
        this.visitdatetime = visitdatetime;
    }

    /**
     * Method of setting customer's check-in Shop
     * @param shop the shop's name you wish to set into a specific visit history
    */
    public void setcustomer_shop(String shop){
        this.shop = shop;
    }

}