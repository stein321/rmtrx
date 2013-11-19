package Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 11/5/13
 * Time: 8:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class GroceryListModel {
    @SerializedName("_id")
    String id;
    String listName;
    List<GroceryListItemModel> groceryListItems;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public List<GroceryListItemModel> getGroceryListItems() {
        return groceryListItems;
    }

    public void setGroceryListItems(List<GroceryListItemModel> groceryListItems) {
        this.groceryListItems = groceryListItems;
    }
}
