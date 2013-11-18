package cap.mizzou.rmtrx.app.grocery;

import Models.GroceryListItemModel;
import Models.GroceryListModel;
import retrofit.Callback;
import retrofit.http.*;

/**
 * Created with IntelliJ IDEA.
 * User: mam8cc
 * Date: 11/1/13
 * Time: 2:16 PM
 * To change this template use File | Settings | File Templates.
 */
public interface GroceryRequestInterface {

    @FormUrlEncoded
    @POST("/list")
    void createList(
            @Field("residence_id") String residenceId ,
            @Field("list_name") String listName,
            Callback<GroceryListModel> callback);

    @FormUrlEncoded
    @POST("/list/item")
    void addItemToList(
            @Field("residence_id") String residenceId,
            @Field("list_id") String listId,
            @Field("item_name") String itemName,
            Callback<GroceryListItemModel> callback);

    @FormUrlEncoded
    @PUT("/list/item")
    void checkBox(
            @Field("residence_id") String residenceId,
            @Field("list_id") String listId,
            @Field("item_id") String itemId,
            @Field("item_status") String isChecked,
            Callback<GroceryListItemModel> callback);
    @FormUrlEncoded
    @DELETE("/list")
    void deleteList(
            @Field("residence_id") String residenceId,
            @Field("list_id") String listId,
            Callback<Void> callback);
    @FormUrlEncoded
    @DELETE("/list/item")
    void deleteItem(
            @Field("residence_id") String residenceId,
            @Field("list_id") String listId,
            @Field("item_id") String itemId,
            Callback<Void> callback);

}
