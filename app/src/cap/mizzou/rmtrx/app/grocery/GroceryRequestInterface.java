package cap.mizzou.rmtrx.app.grocery;

import Models.GroceryListItemModel;
import Models.GroceryListModel;
import Models.UpdateTimeModel;
import retrofit.Callback;
import retrofit.http.*;

import java.util.List;

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

    @DELETE("/residence/{residence_id}/list/{list_id}")
    void deleteList(
            @Path("residence_id") String residenceId,
            @Path("list_id") String listId,
            Callback<Void> callback);


    @DELETE("/residence/{residence_id}/list/{list_id}/item/{item_id}")
    void deleteItem(
            @Path("residence_id") String residenceId,
            @Path("list_id") String listId,
            @Path("item_id") String itemId,
            Callback<Void> callback);


    @GET("/residence/{residence_id}/list/update_time")
    void getUpdateTime(
            @Path("residence_id") String residenceId,
            Callback<UpdateTimeModel> callback);

    @GET("/residence/{residence_id}/list")
    void getLists(
            @Path("residence_id") String residenceId,
            Callback<List<GroceryListModel>> callback);
}
