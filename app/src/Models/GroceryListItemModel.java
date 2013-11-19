package Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 11/5/13
 * Time: 8:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class GroceryListItemModel {


    @SerializedName("_id")
    String id;
    String itemName;
    boolean itemStatus;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(boolean itemStatus) {
        this.itemStatus = itemStatus;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
