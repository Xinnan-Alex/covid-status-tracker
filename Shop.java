/** 
 * Shop class is to handle the data of shops into their respected type of data so that it will be easier for the main program to access them.
 * The datas that should be contain in a Shop class are:
 * String shop_name - The name of the shop,
 * String shop_phonenumber -  the phone number of the shop,
 * String shop_managername -  the manager that incharge of the shop,
 * String shop_status - the status of the shop [could be case/normal]
*/

public class Shop {
    //Private Members
    private String shop_name;
    private String shop_phonenumber;
    private String shop_managername;
    private String shop_status;

    //Methods

    /**
     * Default Constructor
     */
    public Shop() {}

    /**
     * Constructor
     * @param shop_name The name of the shop
     * @param shop_phonenumber The phone number of the shop
     * @param shop_managername The manager name of the shop
     * @param shop_status The status of the shop
     */
    public Shop(String shop_name,String shop_phonenumber,String shop_managername, String shop_status) {
        this.shop_name = shop_name;
        this.shop_phonenumber = shop_phonenumber;
        this.shop_managername = shop_managername;
        this.shop_status = shop_status;
    }
    
    /**
     * Method of converting Shop Object into String for debugging purpose in console
     * @return shop_name shop_phonenumber shop_managername shop_status
    */
    public String toString() {
    return shop_name + " " + shop_phonenumber + " " + shop_managername + " " + shop_status;
    }

    /**
     * Method of converting Shop Objecy into CSV format for easier imports into CSV file
     * @return shop_name,shop_phonenumber,shop_managername,shop_status
     */
    public String toCSVString() {
    return shop_name + "," + shop_phonenumber + "," + shop_managername + "," + shop_status;
    }

    /**
     * Method of getting name of shop
     * @return the name of the shop
     */
    public String getShop_name(){
        return shop_name;
    }

    /**
     * Method of getting the phone number of shop
     * @return the phone number of the shop
     */
    public String getShop_phonenumber(){
        return shop_phonenumber;
    }

    /**
     * Method of getting the manager name of shop
     * @return the manager name of the shop
     */
    public String getShop_managername(){
        return shop_managername;
    }

    /**
     * Method of getting status of shop 
     * @return the status of the shop
     */
    public String getShop_status(){
        return shop_status;
    }

    /**
     * Method of setting the name of shop
     * @param shop_name the name of shop you wish to set
     */
    public void setshop_name(String shop_name){
        this.shop_name = shop_name;
    }

    /**
     * Method of setting the phone number of shop
     * @param shop_phonenumber the phone number of shop you wish to set 
     */
    public void setshop_phonenumber(String shop_phonenumber){
        this.shop_phonenumber = shop_phonenumber;
    }

    /**
     * Method of setting the manager name of shop 
     * @param shop_managername the manger name of shop you wish to set
     */
    public void setshop_managername(String shop_managername){
        this.shop_managername = shop_managername;
    }
    
    /** 
     * Method of setting the status of shop
     * @param shop_status the status of shop you wish to set
    */
    public void setshop_status(String shop_status){
        this.shop_status = shop_status;
    }


}