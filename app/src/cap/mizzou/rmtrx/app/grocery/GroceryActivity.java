package cap.mizzou.rmtrx.app.grocery;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;
import cap.mizzou.rmtrx.app.R;

import java.util.ArrayList;
import java.util.List;

import cap.mizzou.rmtrx.app.grocery.GroceryItem.Sort;


public class GroceryActivity extends Activity {

    /*


    public enum SortMode {
        Alphabetical, Age, Youth;
    }



    public class SortComplex {
        private SortMode sortMode;
        private Sort sort;

        SortComplex(SortMode sortMode) {
            this.sortMode = sortMode;
            setSortByMode();
        }



        public void setSortByMode() {
            switch (checkMode) {
                case Integrated:
                    switch (sortMode) {
                        case Alphabetical:
                            sort = Sort.name;
                            break;
                        case Age:
                            sort = Sort.age;
                            break;
                        case Youth:
                            sort = Sort.youth;
                            break;
                    }
                    break;
                default:
                    switch (sortMode) {
                        case Alphabetical:
                            sort = Sort.status_name;
                            break;
                        case Age:
                            sort = Sort.status_age;
                            break;
                        case Youth:
                            sort = Sort.status_youth;
                            break;
                    }
                    break;
            }
        }

        public SortMode sortMode() {
            return sortMode;
        }



        public boolean setSortModeByMenuItemId(int menuItemId) {
            SortMode oldSortMode = sortMode;
            switch (menuItemId) {
                case R.id.menu_sort_name:
                    sortMode = SortMode.Alphabetical;
                    break;
                case R.id.menu_sort_youth:
                    sortMode = SortMode.Youth;
                    break;
                case R.id.menu_sort_age:
                    sortMode = SortMode.Age;
                    break;
            }
            setSortByMode();
            return sortMode != oldSortMode;
        }



        public int getSortModeMenuId() {
            switch (sortMode) {
                case Alphabetical:
                    return R.id.menu_sort_name;
                case Youth:
                    return R.id.menu_sort_youth;
                case Age:
                    return R.id.menu_sort_age;
            }
            return R.id.menu_sort_name;
        }



        @Override
        public String toString() {
            return sort.toString();
        }

    }

    */






    public static final String AUTHORITY = "cap.mizzou.rmtrx.app.grocery.GroceryDB";

    private Spinner listSpinner;
    private ListView list;
    private EditText newItemEditText;
    private EditText popUpEditText;
    private ImageButton newItemButton;
    private View ItemView;


    private Boolean setSelectedList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grocerymain);
        list = (ListView) findViewById(R.id.List);
        listSpinner = (Spinner) findViewById(R.id.Spinner);
        newItemEditText = (EditText) findViewById(R.id.AddNewEditText);
        newItemButton = (ImageButton) findViewById(R.id.AddNewImageButton);
        newItemButton.setImageResource(android.R.drawable.ic_input_add);
        setupCallbacks();
        loadLists();
    }



    public Cursor getItems() {
        return managedQuery(GroceryItem.ContentUri, GroceryItem.PROJECTION,
                GroceryItem.Columns.ListID + "=?", new String[]{new Long(
                listSpinner.getSelectedItemId()).toString()}, null);
    }




    private void addItem() {
        String text = newItemEditText.getText().toString();
        if (TextUtils.isEmpty(text))
            return;
        getContentResolver().insert(
                GroceryItem.ContentUri,
                GroceryItem.contentValues((int) listSpinner.getSelectedItemId(),
                        newItemEditText.getText().toString()));
        newItemEditText.setText("");
    }




    public void deleteItem() {
        getContentResolver().delete(
                ContentUris.withAppendedId(GroceryItem.ContentUri,
                        ItemView.getId()), null, null);
        loadSelectedList();
    }




    public void setItemChecked(int id, Boolean isChecked) {
        getContentResolver().update(
                ContentUris.withAppendedId(GroceryItem.ContentUri, id),
                GroceryItem.contentValues(isChecked), null, null);
    }



    public Cursor getLists() {
        return managedQuery(GroceryList.ContentUri, GroceryList.PROJECTION,
                null, null, null);
    }



    public void addList(String name) {
        clearDefaultSelected();
        getContentResolver().insert(GroceryList.ContentUri,
                GroceryList.contentValues(name));
    }



    public void deleteList() {
        getContentResolver()
                .delete(
                        GroceryItem.ContentUri,
                        GroceryItem.Columns.ListID + "=?",
                        new String[]{new Long(listSpinner.getSelectedItemId())
                                .toString()});
        getContentResolver().delete(
                ContentUris.withAppendedId(GroceryList.ContentUri, listSpinner
                        .getSelectedItemId()), null, null);
    }



    public List <Integer> getCheckedItemIds()
    {
        Cursor items = getItems();
        List <Integer> checkedItemIds = new ArrayList <Integer>();
        if(items.moveToFirst())	{
            int idColumn = items.getColumnIndex(GroceryItem.Columns._ID);
            int checkedColumn = items.getColumnIndex(GroceryItem.Columns.IsChecked);
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
            getContentResolver().delete(
                    ContentUris.withAppendedId(GroceryItem.ContentUri,
                            id.intValue()), null, null);
        }
    }



    public void uncheckAll() {
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
            addList("Add List");
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
    private final int SortListText = 3;
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

            case SortListText:
                builder = new AlertDialog.Builder(GroceryActivity.this);
                builder.setMessage("Sort?").setCancelable(true)
                        .setPositiveButton("A-Z",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id)
                                    {
                                        //sort
                                        dialog.dismiss();
                                    }
                                }).setNegativeButton("Old-New",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id)
                            {
                            //sort
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
                    .getColumnIndex(GroceryItem.Columns.IsChecked)) == 1);
            checkBox.setOnClickListener(listener);
        }

        public void saveItemChecked(View view) {
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.ItemCheckBox);
            setItemChecked(view.getId(), checkBox.isChecked());
        }
    }



    private final int AddListMenu = 1;
    private final int SortListMenu = 2;
    private final int DeleteCheckedMenu = 4;
    private final int UncheckAllMenu = 5;
    private final int DeleteListMenu = 6;

    // creates menu items
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, AddListMenu, 0, "Add List");
        menu.add(0, DeleteCheckedMenu, 0, "Delete Checked");
        menu.add(0, UncheckAllMenu, 0, "Uncheck All");
        menu.add(0, DeleteListMenu, 0, "Delete List");
        menu.add(0, SortListMenu, 0, "Sort List");

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
            case SortListMenu:
                showDialog(SortListText);
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