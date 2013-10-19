package cap.mizzou.rmtrx.app.Residence;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 10/9/13
 * Time: 11:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class Residence {
    @SerializedName("_id")
    String id;
    String name;
    ArrayList<String> users;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }
}
