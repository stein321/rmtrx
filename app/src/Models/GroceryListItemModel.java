package Models;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 11/5/13
 * Time: 8:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class GroceryListItemModel {
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
}
