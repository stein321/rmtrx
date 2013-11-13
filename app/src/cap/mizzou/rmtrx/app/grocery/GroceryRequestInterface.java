package cap.mizzou.rmtrx.app.grocery;

import Models.GroceryListModel;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

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
}
