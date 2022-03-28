/** 
 * Customer Class is to handle the information of customer so that the program can access them easily.
 * A Customer object should contain the following private members:
 * String Cust_username - The customer's username,
 * String Cust_password - The customer's password,
 * String Cust_fullname - The customer's fullname,
 * String Cust_phonenum - The customer's phone number,
 * String Cust_status - The customer's status [could be normal/case/close]
 */
public class Customer {
    //Private Members
    private String Cust_username;
    private String Cust_password;
    private String Cust_fullname;
    private String Cust_phonenum;
    private String Cust_status;

    //Methods

    /**
     * Default Constructor
     */
    public Customer() {
    }

    /**
     * Constrcutor
     * @param Cust_username the customer's username
     * @param Cust_password the customer's password
     * @param Cust_fullname the customer's fullname
     * @param Cust_phonenum the customer's phone number
     * @param Cust_status the customer's status
     */
    public Customer(String Cust_username, String Cust_password, String Cust_fullname, 
    String Cust_phonenum,String Cust_status) {
        this.Cust_username = Cust_username;
        this.Cust_password = Cust_password;
        this.Cust_fullname = Cust_fullname;
        this.Cust_phonenum = Cust_phonenum;
        this.Cust_status = Cust_status;
    }

    /**
     * Method of converting list into display format in console
     * @return Cust_username Cust_password Cust_fullname Cust_phonenum Cust_status
     */
    public String toString() {
        return Cust_username + " " + Cust_password + " " + Cust_fullname + " " + Cust_phonenum + " " + Cust_status;
    }

    /**
     * Method of converting list into CSV format
     * @return Cust_username,Cust_password,Cust_fullname,Cust_phonenum,Cust_status
     */
    public String toCSVString() {
        return Cust_username + "," + Cust_password + "," + Cust_fullname + "," + Cust_phonenum + "," + Cust_status;
    }

    //Getter
    /**
     * Method of getting customer's username
     * @return the Customer's username
     */
    public String getCust_username(){
        return Cust_username;
    }

    /**
     * Method of getting customer's password
     * @return the Customer's password
     */
    public String getCust_password(){
        return Cust_password;
    }

    /**
     * Method of getting customer's fullname
     * @return the Customer's fullname
     */
    public String getCust_fullname(){
        return Cust_fullname;
    }

    /**
     * Method of getting customer's phone number
     * @return the Customer's phone number
     */
    public String getCust_phonenum(){
        return Cust_phonenum;
    }

    /**
     * Method of getting customer's status
     * @return the Customer's status
     */
    public String getCust_status(){
        return Cust_status;
    }

    //Setter
    /**
     * Method of setting customer's username
     * @param Cust_username the customer's username
     */
    public void set_Customerusername(String Cust_username){
        this.Cust_username = Cust_username;
    }

    /**
     * Method of setting customer's password
     * @param Cust_password the customer's password
     */
    public void set_Customerpassword(String Cust_password){
        this.Cust_password = Cust_password;
    }

    /**
     * Method of setting customer's fullname
     * @param Cust_fullname the customer's fullname
     */
    public void set_Customerfullname(String Cust_fullname){
        this.Cust_fullname = Cust_fullname;
    }

    /**
     * Method of setting customer's phone number
     * @param Cust_phonenum the customer's phone number
     */
    public void set_Customerphonenum(String Cust_phonenum){
        this.Cust_phonenum = Cust_phonenum;
    }

    /**
     * Method of setting customer's status
     * @param Cust_status the customer's status
     */
    public void set_Customerstatus(String Cust_status){
        this.Cust_status = Cust_status;
    }

}