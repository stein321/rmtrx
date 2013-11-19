package cap.mizzou.rmtrx.app.grocery;

import Models.GroceryListItemModel;
import Models.GroceryListModel;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;
import cap.mizzou.rmtrx.app.R;
import cap.mizzou.rmtrx.app.User_setup.UserInfo;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.List;

public class GroceryActivity extends Activity {

    public static final String AUTHORITY = "cap.mizzou.rmtrx.app.grocery.GroceryDB";

    private Spinner listSpinner;
    private ListView list;
    private EditText newItemEditText;
    private EditText popUpEditText;
    private ImageButton newItemButton;
    private View ItemView;
    private RestAdapter restAdapter;
    private GroceryRequestInterface restInterface;
    private UserInfo userInfo;
    private Boolean setSelectedList;

    private String listIdLocal;
    private String listIdOnServer;
    private String itemIdLocal;
    private String itemIdOnServer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grocerymain);
        list = (ListView) findViewById(R.id.List);
        listSpinner = (Spinner) findViewById(R.id.Spinner);
        newItemEditText = (EditText) findViewById(R.id.AddNewEditText);
        newItemButton = (ImageButton) findViewById(R.id.AddNewImageButton);
        newItemButton.setImageResource(android.R.drawable.ic_input_add);
        restAdapter = new RestAdapter.Builder().setServer("http://powerful-thicket-5732.herokuapp.com/").build();
        restInterface = restAdapter.create(GroceryRequestInterface.class);
        Context context=getApplicationContext();
        userInfo=new UserInfo(context);
        setupCallbacks();
        loadLists();
    }



    public Cursor getItems() {
        return managedQuery(GroceryItem.ContentUri, GroceryItem.PROJECTION,
                GroceryItem.Columns.LIST_ID + "=?", new String[]{new Long(
                listSpinner.getSelectedItemId()).toString()}, null);
    }




    private void addItem() {
        String itemName = newItemEditText.getText().toString();

        if (TextUtils.isEmpty(itemName))
            return;
        getContentResolver().insert(
                GroceryItem.ContentUri,
                GroceryItem.contentValues((int) listSpinner.getSelectedItemId(),
                        newItemEditText.getText().toString()));


        newItemEditText.setText("");

        //get serviceIdOfTheList
        String listId=getListServiceId(String.valueOf(listSpinner.getSelectedItemId()));
        sendItemToServer(itemName, listId);
    }
    private String getListServiceId(String id) {
        String selectionClause= GroceryList.Columns._ID + " = ? ";
        String selectionArgs[]={id};
        String projection[]={GroceryList.Columns.SERVICE_ID};
        Cursor cursor=getContentResolver().query(GroceryList.ContentUri,projection ,selectionClause, selectionArgs,null);
        cursor.moveToFirst();
        int index=cursor.getColumnIndex(GroceryList.Columns.SERVICE_ID);
        return cursor.getString(index);
    }
    private String getItemServiceId(String id) {   //should return the serviceId when sent the localid
        String projection[]={GroceryItem.Columns.SERVICE_ID};
        String selectionClause= GroceryItem.Columns._ID + " = ? ";
        String selectionArgs[]={id};
        Cursor cursor=getContentResolver().query(GroceryItem.ContentUri,null ,selectionClause, selectionArgs,null);
        int index=cursor.getColumnIndex(GroceryItem.Columns.SERVICE_ID);
        cursor.moveToFirst();
        return cursor.getString(index);
    }

    private void sendItemToServer(String itemName, String listId) {
        restInterface.addItemToList(userInfo.getResidenceId(), listId, itemName, new Callback<GroceryListItemModel>() {
            @Override
            public void success(GroceryListItemModel groceryListItemModel, Response response) {
                updateItemInLocalDb(groceryListItemModel.getId());
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }



    private void updateItemInLocalDb(String serviceItemId) {
        ContentValues contentValues= new ContentValues();
        contentValues.put(GroceryItem.Columns.SERVICE_ID,serviceItemId);
        int idOfItem = getLastInsertedItemId();
        String selectionArgs[]={String.valueOf(idOfItem)};
        String selectionClause=GroceryItem.Columns._ID + " = ? ";
        int mCursor= getContentResolver().update(GroceryItem.ContentUri, contentValues, selectionClause, selectionArgs);

        String[] ids= {serviceItemId};
        Cursor cursor=getContentResolver().query(GroceryItem.ContentUri,null,GroceryItem.Columns.SERVICE_ID + " = ?",ids,null);
          cursor.moveToFirst();
        String ID= cursor.getString(0);

//        Cursor cursor=getContentResolver().query(GroceryItem.ContentUri,null,selectionClause,selectionArgs,null);
//        cursor.moveToNext();
//        while(cursor!=null) {
//            cursor.moveToFirst();
//            String message=cursor.getString(0);
//            Log.d("cursor string 1",cursor.getString(1));
//            Log.d("cursor string 2",cursor.getString(2));
//            Log.d("cursor string 3",cursor.getString(3));
//            Log.d("cursor string 4",cursor.getString(4));
//            Log.d("cursor string 5",cursor.getString(5));

//
//        }
    }




    public void deleteItem() {
        getContentResolver().delete(
                ContentUris.withAppendedId(GroceryItem.ContentUri, ItemView.getId()), null, null);
        deleteItemOnServer(getListServiceId(String.valueOf(ItemView.getId())));
        loadSelectedList();

    }

    private void deleteItemOnServer(String id) {
        String listId=getListServiceId(String.valueOf(listSpinner.getSelectedItemId()));

        restInterface.deleteItem(userInfo.getResidenceId(), listId, id, new Callback<Void>() {
            @Override
            public void success(Void aVoid, Response response) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

    }


    public void setItemChecked(int id, Boolean isChecked) {
        getContentResolver().update(
                ContentUris.withAppendedId(GroceryItem.ContentUri, id),
                GroceryItem.contentValues(isChecked), null, null);
        String itemServiceId=getItemServiceId(String.valueOf(id));

       setItemCheckedOnServer(itemServiceId, String.valueOf(isChecked));

    }

    private void setItemCheckedOnServer(String itemId,String isChecked) {
        String listServiceId=getListServiceId(String.valueOf(listSpinner.getSelectedItemId()));
        restInterface.checkBox(userInfo.getResidenceId(), listServiceId, itemId, isChecked, new Callback<GroceryListItemModel>() {
            @Override
            public void success(GroceryListItemModel groceryListItemModel, Response response) {
                Log.d("itemCheckedOnServer", "success");
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }


    public Cursor getLists() {
        return managedQuery(GroceryList.ContentUri, GroceryList.PROJECTION,
                null, null, null);
    }


    public void addList(String listName) {
        saveListToLocalDB(listName);

        restInterface.createList(userInfo.getResidenceId(), listName, new Callback<GroceryListModel>() {
            @Override
            public void success(GroceryListModel groceryListModel, Response response) {
                updateListInLocalDb(groceryListModel.getId());

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

    }

    private void updateListInLocalDb(String serviceId) {
        ContentValues contentValues= new ContentValues();
        contentValues.put(GroceryList.Columns.SERVICE_ID,serviceId);
        int idOfList = getLastInsertedListId();
        String selectionArgs[]={String.valueOf(idOfList)};
        String selectionClause= GroceryList.Columns._ID + " = ? ";
        getContentResolver().update(GroceryList.ContentUri, contentValues, selectionClause, selectionArgs);
    }

    private void saveListToLocalDB(String listName) {
       ContentValues contentValues=new ContentValues();
       contentValues.put(GroceryList.Columns.SERVICE_ID, "");
       contentValues.put(GroceryList.Columns.NAME,listName);
        clearDefaultSelected();
       getContentResolver().insert(GroceryList.ContentUri, contentValues);
    }
    private int getLastInsertedListId() {
        int id;
        Cursor cursor = getContentResolver().query(GroceryList.ContentUri,null,null,null,GroceryList.Columns.CreatedDate);
        if(cursor == null) {
            return -1;
        }
        else {
            cursor.moveToLast();
            int index=cursor.getColumnIndex(GroceryList.Columns._ID);
            id=cursor.getInt(index);
        }
        listIdLocal= String.valueOf(id);
        return id;
    }
    private int getLastInsertedItemId() {
        int id;
        Cursor cursor = getContentResolver().query(GroceryItem.ContentUri,null,null,null,GroceryItem.Columns.CREATED_DATE);
        if(cursor == null) {
            return -1;
        }
        else {
            cursor.moveToLast();
            int index=cursor.getColumnIndex(GroceryItem.Columns._ID);
            id=cursor.getInt(index);
        }
        return id;
    }

    public void deleteList() {
        deleteListOnServer(String.valueOf(listSpinner.getSelectedItemId()));
        getContentResolver()
                .delete(
                        GroceryItem.ContentUri,
                        GroceryItem.Columns.LIST_ID + "=?",
                        new String[]{new Long(listSpinner.getSelectedItemId())
                                .toString()});
        getContentResolver().delete(
                ContentUris.withAppendedId(GroceryList.ContentUri, listSpinner
                        .getSelectedItemId()), null, null);
    }

    private void deleteListOnServer(String selectedItemId) {
        String id=getListServiceId(selectedItemId);
        restInterface.deleteList(userInfo.getResidenceId(),id, new Callback<Void>() {
            @Override
            public void success(Void aVoid, Response response) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }


    public List <Integer> getCheckedItemIds()
    {
        Cursor items = getItems();
        List <Integer> checkedItemIds = new ArrayList <Integer>();
        if(items.moveToFirst())	{
            int idColumn = items.getColumnIndex(GroceryItem.Columns._ID);
            int checkedColumn = items.getColumnIndex(GroceryItem.Columns.IS_CHECKED);
            do {
                if(items.getInt(checkedColumn) == 1) {
                    checkedItemIds.add(new Integer(items.getInt(idColumn)));
                }
            } while (items.moveToNext());
        }
        return checkedItemIds;
    }




    public void deleteCheckedEntries() {
        List <Integer> checkedItemIds = getCheckedItemIds();
        for(Integer id: checkedItemIds) {
            String serverId=getItemServiceId(String.valueOf(id.intValue()));
            deleteItemOnServer(serverId);
            getContentResolver().delete(
                    ContentUris.withAppendedId(GroceryItem.ContentUri,
                            id.intValue()), null, null);

        }
    }



    public void uncheckAll() {     //TODO:uncheck all
        List <Integer> checkedItemIds = getCheckedItemIds();
        for(Integer id: checkedItemIds) {
            setItemChecked(id, false);
        }
    }



    public void setListSelected() {
        clearDefaultSelected();
        getContentResolver().update(
                ContentUris.withAppendedId(GroceryList.ContentUri, listSpinner
                        .getSelectedItemId()),
                GroceryList.contentValues(true), null, null);
    }


    private void clearDefaultSelected() {
        getContentResolver().update(GroceryList.ContentUri,
                GroceryList.contentValues(false), null, null);
    }



    private void setupCallbacks() {
        listSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView <?> arg0, View view,
                                       int position, long id) {
                if (setSelectedList) {
                    setListSelected();
                    loadSelectedList();
                }
            }

            public void onNothingSelected(AdapterView <?> arg0) {
            }

        });


        newItemButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                addItem();
            }
        });
    }




    private void loadLists() {
        Cursor cursor = getLists();

        if (!cursor.moveToFirst()) {
            Dialog dialog = onCreateDialog(2);
            dialog.show();
            cursor = getLists();
        }

        String[] from = new String[] { GroceryList.Columns.NAME };
        int[] to = new int[] { android.R.id.text1 };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_item, cursor, from, to);
        adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        setSelectedList = false;
        listSpinner.setAdapter(adapter);
        setSelectedList = true;

        loadSelectedList();
    }



    private void loadSelectedList() {
        SimpleItemToCheckBoxAdapter adapter = new SimpleItemToCheckBoxAdapter(
                this, getItems());
        list.setAdapter(adapter);
        if(list.getChildCount() > 0) {

            list.requestFocus();
        }
    }



    private final int DeleteItemText = 1;
    private final int ListAddText = 2;
    //
    private final int DeleteListText = 4;
    //



    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        AlertDialog.Builder builder;
        Context mContext = getApplicationContext();
        LayoutInflater inflater;
        View layout;


        switch (id) {
            case DeleteItemText:
                builder = new AlertDialog.Builder(GroceryActivity.this);
                builder.setMessage("delete item?").setCancelable(true)
                        .setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        deleteItem();
                                        dialog.dismiss();
                                    }
                                }).setNegativeButton("no",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });

                dialog = builder.create();
                break;
            case ListAddText:
                inflater = (LayoutInflater) mContext
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                layout = inflater.inflate(R.layout.grocerytext, null);
                popUpEditText = (EditText) layout.findViewById(R.id.EditText);
                popUpEditText.setText("");


                builder = new AlertDialog.Builder(GroceryActivity.this);
                builder.setView(layout).setMessage("add list").setCancelable(true)
                        .setPositiveButton("add",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        if (!TextUtils.isEmpty(popUpEditText
                                                .getText().toString())) {
                                            addList(popUpEditText.getText()
                                                    .toString());
                                            loadLists();
                                            dialog.dismiss();
                                        }
                                    }
                                }).setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });
                dialog = builder.create();
                break;


            case DeleteListText:
                builder = new AlertDialog.Builder(GroceryActivity.this);
                builder.setMessage("delete list?").setCancelable(true)
                        .setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        deleteList();
                                        loadLists();
                                        dialog.dismiss();
                                    }
                                }).setNegativeButton("no",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });
                dialog = builder.create();
                break;

        }
        return dialog;
    }



    private class SimpleItemToCheckBoxAdapter extends ResourceCursorAdapter {

        public SimpleItemToCheckBoxAdapter(Context context, Cursor cur) {
            super(context, R.layout.item, cur);
        }

        @Override
        public View newView(Context context, Cursor cur, ViewGroup parent) {
            LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return li.inflate(R.layout.item, parent, false);}

        @Override
        public void bindView(View view, Context context, Cursor cur) {
            view.setId(cur.getInt(cur.getColumnIndex(GroceryItem.Columns._ID)));

            View.OnClickListener listener = new View.OnClickListener() {
                public void onClick(View view) {
                    if (view instanceof CheckBox) {
                        saveItemChecked((View) view.getParent());
                    } else {
                        CheckBox checkBox =
                                (CheckBox)view.findViewById(R.id.ItemCheckBox);
                        checkBox.setChecked(!checkBox.isChecked());
                        saveItemChecked(view);
                    }
                }
            };


            View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    ItemView = v;
                    showDialog(DeleteItemText);
                    return true;
                }
            };

            view.setOnClickListener(listener);
            view.setOnLongClickListener(longClickListener);

            TextView textView = (TextView) view.findViewById(R.id.ItemTextView);
            textView.setText(cur.getString(cur
                    .getColumnIndex(GroceryItem.Columns.NAME)));

            CheckBox checkBox = (CheckBox) view.findViewById(R.id.ItemCheckBox);
            checkBox.setChecked(cur.getInt(cur
                    .getColumnIndex(GroceryItem.Columns.IS_CHECKED)) == 1);
            checkBox.setOnClickListener(listener);
        }

        public void saveItemChecked(View view) {
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.ItemCheckBox);
            setItemChecked(view.getId(), checkBox.isChecked());
        }
    }



    private final int AddListMenu = 1;

    private final int DeleteCheckedMenu = 4;
    private final int UncheckAllMenu = 5;
    private final int DeleteListMenu = 6;

    // creates menu items
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, AddListMenu, 0, "Add List");
        menu.add(0, DeleteCheckedMenu, 0, "Delete Checked");
        menu.add(0, UncheckAllMenu, 0, "Uncheck All");
        menu.add(0, DeleteListMenu, 0, "Delete List");
        return true;
    }



    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case AddListMenu:
                showDialog(ListAddText);
                return true;
            case DeleteListMenu:
                showDialog(DeleteListText);
                return true;

            case DeleteCheckedMenu:
                deleteCheckedEntries();
                loadSelectedList();
                return true;
            case UncheckAllMenu:
                uncheckAll();
                loadSelectedList();
                return true;
        }
        return false;
    }
}