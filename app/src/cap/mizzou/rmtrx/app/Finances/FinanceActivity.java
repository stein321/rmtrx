package cap.mizzou.rmtrx.app.Finances;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

import cap.mizzou.rmtrx.app.DataAccess.Resident;
import cap.mizzou.rmtrx.app.DataAccess.ResidentDataSource;
import cap.mizzou.rmtrx.app.R;

import java.util.*;


import cap.mizzou.rmtrx.app.User_setup.UserInfo;
import retrofit.RestAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: Desi
 * Date: 11/12/13
 * Time: 4:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class FinanceActivity extends ListActivity {

    private FinanceDb dataSource;
    private String description;
    private Double amount;
    private UserInfo userinfo;
    private String userid;

    private Spinner spinner;
    private int index;
    private TextView t;
    private ResidentDataSource data;
    private financesInterface restInterface;
    List<Resident> allResidents;
    List<String> residentWithTabList;
    private RestAdapter restAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finances);
//        getActionBar().setTitle("Finances");

        userinfo= new UserInfo(this);
        userid=userinfo.getId();

        dataSource = new FinanceDb(this);
        dataSource.open();
        data = new ResidentDataSource(this);
        data.open();
        restAdapter = new RestAdapter.Builder().setServer("http://powerful-thicket-5732.herokuapp.com/").build();
        restInterface = restAdapter.create(financesInterface.class);

        //initialize Spinner
        spinner = (Spinner) findViewById(R.id.other_roommates);

        residentWithTabList = new ArrayList<String>();
        allResidents=new ArrayList<Resident>();


//        allResidents =data.getAllResidents();
         updateSpinner();
        //get index of currentUser   so it can be removed from the list

    }

    public void updateSpinner() {
            residentWithTabList.clear();
//            allResidents.clear();
            allResidents =data.getAllResidents();

        for(int i=0;i<allResidents.size();i++) {
            if(allResidents.get(i).getUserID().equals(userinfo.getId())) {
                index=i;
            }
        }
        allResidents.remove(index);

        for(Resident resident: allResidents) {
            residentWithTabList.add(resident.getFirstName() + " $ " + String.valueOf(dataSource.amountOwed(userinfo.getId(), resident.getUserID())));
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, residentWithTabList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    public void addTransaction(View v){

        Transaction transaction;
        spinner = (Spinner) findViewById(R.id.other_roommates);

        setFormInputAndClear();


        //Spinner selection

        String roomateUserFirstName=String.valueOf(spinner.getSelectedItem());
        int index= spinner.getSelectedItemPosition();
        //getUserIdFromSpinner
        Resident to= allResidents.get(index);
        String toId= to.getUserID();




        dataSource.createTransaction(userinfo.getId(), toId, description, amount);
        updateSpinner();

        createToast(amount,roomateUserFirstName);
    }

    private void createToast(Double amount, String roomateUserFirstName) {
        CharSequence text = "Transaction of $" + amount + " was added to " + roomateUserFirstName + "'s Account.";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(this, text, duration);
        toast.setGravity(Gravity.TOP|Gravity.LEFT, 0, 0);
        toast.show();
    }

    public void addGroupTransaction(View view) {
        setFormInputAndClear();
        new AlertDialog.Builder(this)
                .setTitle("Group Transaction")
                .setMessage("Are you sure you want to charge everyone" + " $" + amount + " ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        for(Resident resident: allResidents) {
                            dataSource.createTransaction(userinfo.getId(), resident.getUserID(), description, amount); ;
                        }
                        updateSpinner();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .show();





    }
    private void setFormInputAndClear() {
        //grab text from form
        EditText amountText = (EditText) findViewById(R.id.transaction_amount);
        EditText descriptionText=(EditText)findViewById(R.id.transaction_nature);
        //Converts text to double
        amount=Double.parseDouble(amountText.getText().toString());
        description=descriptionText.getText().toString();
        //clear form
        descriptionText.setText("");
        amountText.setText("");
    }

    @Override
    protected void onResume() {
        dataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }

}



