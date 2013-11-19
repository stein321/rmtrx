package Models;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 11/4/13
 * Time: 8:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class Residence {
    String name;
    @SerializedName("_id")
    String id;
    User[] users;
    List<GroceryListModel> groceryLists;
    long groceryListLastUpdate;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public List<GroceryListModel> getGroceryLists() {
        return groceryLists;
    }

    public void setGroceryLists(List<GroceryListModel> groceryLists) {
        this.groceryLists = groceryLists;
    }

    public long getGroceryListLastUpdate() {
        return groceryListLastUpdate;
    }

    public void setGroceryListLastUpdate(long groceryListLastUpdate) {
        this.groceryListLastUpdate = groceryListLastUpdate;
    }

    public Date getTimestampAsDate(int timestamp) {
        Timestamp stamp = new Timestamp(timestamp);
        return new Date(stamp.getTime());
    }
}
