package cap.mizzou.rmtrx.app.DataAccess;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 11/8/13
 * Time: 7:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class Resident {

    private long id;
    private String userID;
    private String email;
    private String firstName;
    private String lastName;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String toString() {
        return getEmail();
    }



}
