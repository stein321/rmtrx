package cap.mizzou.rmtrx.app.Finances;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import cap.mizzou.rmtrx.app.DataAccess.Resident;
import cap.mizzou.rmtrx.app.DataAccess.ResidentDataSource;
import cap.mizzou.rmtrx.app.R;

import java.util.*;


import cap.mizzou.rmtrx.app.User_setup.UserInfo;

/**
 * Created with IntelliJ IDEA.
 * User: Desi
 * Date: 11/12/13
 * Time: 4:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class FinanceActivity extends ListActivity {

    private FinanceDb datasource;

    private double accountbalance;
    private String abText;
    TextView tv;
    private UserInfo userinfo;
    private String userid;

    private Spinner spinner;


    private ResidentDataSource data;      //stein
    List<Resident> residents;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finances);
        getActionBar().setTitle("Finances");

        userinfo= new UserInfo(this);
        userid=userinfo.getId();

        datasource = new FinanceDb(this);
        datasource.open();
        data = new ResidentDataSource(this);
        data.open();


//         //Gets total account balance
//         accountbalance=getAccountBalance(userid);
//         //Converts double to String
//         abText= String.valueOf(accountbalance);
//         abText= "$" + abText;
//         //Grabs Texview
//        tv= (TextView)findViewById(R.id.account_balance);
//         //Fills Textview with accountbalance
//        tv.setText(abText);


        //Spinner
        spinner = (Spinner) findViewById(R.id.other_roommates);
        List<String> list = new ArrayList<String>();
        residents=data.getAllResidents();

        for(Resident resident : residents) {
            list.add(resident.getFirstName());// + getTabWithUser(resident.getUserID()));
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

}

    private String getTabWithUser(String userid) {
             double profit=getAccountBalance(userinfo.getId(), userid) - getAccountBalance(userid, userinfo.getId());
        return " $" + String.valueOf(profit);
    }

    public void addTransaction(View v){

        Transaction transaction;
        spinner = (Spinner) findViewById(R.id.other_roommates);


        EditText amountText = (EditText) findViewById(R.id.transaction_amount);
        //Converts text to double
        Double amount=Double.parseDouble(amountText.getText().toString());

        //Spinner selection
        String roomateUserId=String.valueOf(spinner.getSelectedItem());
        int index= spinner.getSelectedItemPosition();
        //getUserIdFromSpinner
        Resident to=residents.get(index);
        String toId= to.getUserID();

        //TODO: add a description box
        String description="Mike for breakfast";

        amountText.setText("");
        //Sends transaction info to record creation method
        transaction=datasource.createTransaction(userinfo.getId(),toId,description,amount);

        //add toast message
        Context context = getApplicationContext();
        CharSequence text = "Transaction of $" + amount + " was added to " + roomateUserId + "'s Account.";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public double getAccountBalance(String from, String to ){
        double ab=0;
        List<Transaction> values = datasource.getAllTransactions(from,to);

        for (int i = 0; i < values.size(); i++) {
            ab= ab+ values.get(i).getAmount();
        }

        return ab;
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

}



