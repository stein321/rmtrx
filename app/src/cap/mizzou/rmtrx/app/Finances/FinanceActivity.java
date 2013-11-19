package cap.mizzou.rmtrx.app.Finances;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.text.format.*;
import cap.mizzou.rmtrx.app.BulletinBoard.AddBBPostActivity;
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



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finances);
        getActionBar().setTitle("Finances");

        userinfo= new UserInfo(this);
        userid=userinfo.getId();





        datasource = new FinanceDb(this);
        datasource.open();


        //drops database table
        /*
        datasource.drop();
          */

         //Gets total account balance
         accountbalance=getAccountBalance(userid);
         //Converts double to String
         abText= String.valueOf(accountbalance);
         abText= "$" + abText;
         //Grabs Texview
        tv= (TextView)findViewById(R.id.account_balance);
         //Fills Textview with accountbalance
        tv.setText(abText);


        //Spinner
        spinner = (Spinner) findViewById(R.id.other_roommates);
        //TODO Ben: Change hardcode to actual roommate info; SPINNER WILL HAVE THE NAMES OF THE OTHER ROOMMATES
        List<String> list = new ArrayList<String>();
        list.add("Ryan");
        list.add("Jim");
        list.add("Brad");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

}
    public void addTransaction(View v){

        Transaction transaction;
        spinner = (Spinner) findViewById(R.id.other_roommates);


        EditText amountText = (EditText) findViewById(R.id.transaction_amount);
        EditText natureText = (EditText) findViewById(R.id.transaction_nature);
        //Converts text to double
        Double amount=Double.parseDouble(amountText.getText().toString());
        String nature=natureText.getText().toString();
        //Spinner selection
        String roomateUserId=String.valueOf(spinner.getSelectedItem());
        //Retrieves current time and date
        String currentdate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        //Nature of transaction

        amountText.setText("");
        natureText.setText("");
        //Sends transaction info to record creation method
        transaction=datasource.createTransaction(roomateUserId, amount, nature, currentdate);

        //add toast message
        Context context = getApplicationContext();
        CharSequence text ="Transaction of $" + amount + " was added to " + roomateUserId + "'s Account on " + transaction.getDate() + ". " + transaction.getNature();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void addGroupTransaction(View view){
        Transaction transaction;

        EditText amountText = (EditText) findViewById(R.id.transaction_amount);
        EditText natureText = (EditText) findViewById(R.id.transaction_nature);
        //Converts text to double
        Double totalamount=Double.parseDouble(amountText.getText().toString());
        String nature=natureText.getText().toString();
        String currentdate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());


        //Gets number of roommates from the amount of spinner items
        int numberofroommates=spinner.getAdapter().getCount() + 1;

        double divisor=(double) numberofroommates;

        double amount= totalamount/divisor;
        //TODO: Hardcoded info, CHANGE TO ACTUAL OTHER ROOMMATE USERIDS
        transaction=datasource.createTransaction("Ryan", amount, nature, currentdate);
        transaction=datasource.createTransaction("Jim", amount, nature, currentdate);
        transaction=datasource.createTransaction("Brad", amount, nature, currentdate);

        transaction=datasource.createTransaction(userid, amount, nature, currentdate);

        amountText.setText("");
        natureText.setText("");
    }

    public double getAccountBalance(String userid){
        double ab=0;
        List<Transaction> values = datasource.getAllTransactions(userid);

        for (int i = 0; i < values.size(); i++) {
            ab= ab+ values.get(i).getAmount();
        }

        return ab;
    }

    public void generateBill(View view){

        Intent generatebill= new Intent(this, GenerateBillActivity.class);
        startActivity(generatebill);
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



