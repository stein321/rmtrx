package Models;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 11/5/13
 * Time: 1:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResponseObject {
    private Key key;
    private User user;
    private Residence residence;

    public Key getResponse() {
        return key;
    }

    public void setResponse(Key key) {
        this.key = key;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public Residence getResidence() {
        return residence;
    }

    public void setResidence(Residence residence) {
        this.residence = residence;
    }


}