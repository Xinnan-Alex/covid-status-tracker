/**
 * InvalidUserTypeException will the thrown if a user select the wrong usertype 
 */
public class InvalidUserTypeException extends Exception {
    
    /**
     * Default Constructor
     */
    public InvalidUserTypeException() {
    };
    
    /**
     * Constructor
     * @param usertype The invalid usertype the user selected 
     */
    public InvalidUserTypeException(String usertype){
        super("Invalid Usertype: " + usertype + "\nPlease try again");
    }
}