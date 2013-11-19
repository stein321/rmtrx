package cap.mizzou.rmtrx.app.DataAccess;

import Models.*;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import cap.mizzou.rmtrx.app.User_setup.UserInfo;
import cap.mizzou.rmtrx.app.grocery.GroceryItem;
import cap.mizzou.rmtrx.app.grocery.GroceryList;
import cap.mizzou.rmtrx.app.grocery.GroceryRequestInterface;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mam8cc
 * Date: 11/17/13
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseHydrator {

    private Context context;
    private RestAdapter restAdapter;
    private ResidenceDataInterface residenceRestInterface;
    private GroceryRequestInterface groceryRequestInterface;

    public DatabaseHydrator(Context context) {
        this.context = context;
        restAdapter   =new RestAdapter.Builder().setServer("http://powerful-thicket-5732.herokuapp.com/").build();
        residenceRestInterface = restAdapter.create(ResidenceDataInterface.class);
        groceryRequestInterface = restAdapter.create(GroceryRequestInterface.class);
    }

    public void UpdateDatabase(String residenceId) {
        residenceRestInterface.getResidence(residenceId, new Callback<Residence>() {
            @Override
            public void success(Residence residence, Response response) {
                ContentResolver resolver = context.getContentResolver();

                UserInfo userInfo = new UserInfo(context);
                userInfo.setGroceryListLastUpdate(residence.getGroceryListLastUpdate());
                ResidentDataSource residentDataSource = new ResidentDataSource(context);
                residentDataSource.open();

                for (User user : residence.getUsers()) {
                    residentDataSource.addResident(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName());
                }

                residentDataSource.close();

                List<GroceryListModel> groceryLists = residence.getGroceryLists();

                if (groceryLists != null) {
                    for (GroceryListModel list : residence.getGroceryLists()) {
                        ContentValues values = new ContentValues();
                        values.put(GroceryList.Columns.NAME, list.getListName());
                        values.put(GroceryList.Columns.SERVICE_ID, list.getId());

                        resolver.insert(GroceryList.ContentUri, values);

                        List<GroceryListItemModel> groceryListItems = list.getGroceryListItems();
                        if (groceryListItems != null) {
                            for (GroceryListItemModel item : groceryListItems) {
                                ContentValues itemValues = new ContentValues();
                                itemValues.put(GroceryItem.Columns.NAME, item.getItemName());
                                itemValues.put(GroceryItem.Columns.IS_CHECKED, item.isItemStatus());
                                itemValues.put(GroceryItem.Columns.SERVICE_ID, item.getId());

                                resolver.insert(GroceryItem.ContentUri, itemValues);
                            }
                        }
                    }
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                //To change body of implemented methods use File | Settings | File Templates.
                int x = 1;
            }
        });

    }

    public void updateGroceryDatabase(String residenceId) {
    groceryRequestInterface.getLists(residenceId, new Callback<List<GroceryListModel>>() {

        @Override
        public void success(List<GroceryListModel> groceryListModels, Response response) {
            ContentResolver resolver = context.getContentResolver();
            for (GroceryListModel list : groceryListModels) {
                ContentValues values = new ContentValues();
                values.put(GroceryList.Columns.NAME, list.getListName());
                values.put(GroceryList.Columns.SERVICE_ID, list.getId());

                resolver.insert(GroceryList.ContentUri, values);

                List<GroceryListItemModel> groceryListItems = list.getGroceryListItems();
                if (groceryListItems != null) {
                    for (GroceryListItemModel item : groceryListItems) {
                        ContentValues itemValues = new ContentValues();
                        itemValues.put(GroceryItem.Columns.NAME, item.getItemName());
                        itemValues.put(GroceryItem.Columns.IS_CHECKED, item.isItemStatus());
                        itemValues.put(GroceryItem.Columns.SERVICE_ID, item.getId());

                        resolver.insert(GroceryItem.ContentUri, itemValues);
                    }
                }
            }
        }


        @Override
        public void failure(RetrofitError retrofitError) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    });
    }
}
