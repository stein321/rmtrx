package cap.mizzou.rmtrx.app.Finances;

import Models.TransactionCallback;
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
import cap.mizzou.rmtrx.app.grocery.GroceryRequestInterface;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
    private int index;
    private TextView t;
    private ResidentDataSource data;
    private financesInterface restInterface;
    List<Resident> allResidents;

    private RestAdapter restAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finances);
//        getActionBar().setTitle("Finances");

        userinfo= new UserInfo(this);
        userid=userinfo.getId();

        datasource = new FinanceDb(this);
        datasource.open();
        data = new ResidentDataSource(this);
        data.open();
        restAdapter = new RestAdapter.Builder().setServer("http://powerful-thicket-5732.herokuapp.com/").build();
        restInterface = restAdapter.create(financesInterface.class);

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
        allResidents =data.getAllResidents();

        //get index of currentUser   so it can be removed from the list
        for(int i=0;i<allResidents.size();i++) {
            if(allResidents.get(i).getUserID().equals(userinfo.getId())) {
                  index=i;
            }
        }
        allResidents.remove(index);

        for(Resident resident: allResidents) {
                list.add(resident.getFirstName() + " $ " + String.valueOf(datasource.amountOwed(userinfo.getId(),resident.getUserID())));
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

//        t=(TextView)findViewById(R.id.Owed);


}

//    private String getTabWithUser() {
//                String amount= String.valueOf(datasource.amountOwed(userinfo.getId(),));
//
////             double profit=getAccountBalance(userinfo.getId(), userid) - getAccountBalance(userid, userinfo.getId());
//        return amount;// + String.valueOf(profit);
//    }

    public void addTransaction(View v){

        Transaction transaction;
        spinner = (Spinner) findViewById(R.id.other_roommates);


        EditText amountText = (EditText) findViewById(R.id.transaction_amount);
        //Converts text to double
        Double amount=Double.parseDouble(amountText.getText().toString());

        //Spinner selection

        String roomateUserFirstName=String.valueOf(spinner.getSelectedItem());
        int index= spinner.getSelectedItemPosition();
        //getUserIdFromSpinner
        Resident to= allResidents.get(index);
        String toId= to.getUserID();

        String description="Mike for breakfast";

        amountText.setText("");
        //Sends transaction info to record creation method

        datasource.createTransaction(userinfo.getId(),toId,description,amount);

//        sendTransactionToServer(toId,description,amount);

        String number= String.valueOf(datasource.amountOwed(userinfo.getId(),toId));
        t.setText(number);


        //        //add toast message
//        Context context = getApplicationContext();
//        CharSequence text = "Transaction of $" + amount + " was added to " + roomateUserFirstName + "'s Account.";
//        int duration = Toast.LENGTH_SHORT;
//
//        Toast toast = Toast.makeText(context, text, duration);
//        toast.show();
//        datasource.getAllTransactions(userinfo.getId());
    }

//    private void sendTransactionToServer(String toId, String description, Double amount) {
//        restInterface.sendTransaction(userinfo.getResidenceId(),userinfo.getId(),toId,description , String.valueOf(amount),new Callback<TransactionCallback>() {
//            @Override
//            public void success(TransactionCallback transactionCallback, Response response) {
//                //To change body of implemented methods use File | Settings | File Templates.
//            }
//
//            @Override
//            public void failure(RetrofitError retrofitError) {
//                //To change body of implemented methods use File | Settings | File Templates.
//            }
//        });
//    }

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



