package cap.mizzou.rmtrx.app.Finances;

import android.os.Bundle;
import cap.mizzou.rmtrx.app.R;
import android.content.Intent;
import android.view.View;
import android.app.Activity;

/**
 * Created with IntelliJ IDEA.
 * User: Desi
 * Date: 10/19/13
 * Time: 7:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class FinancesActivity extends Activity{
    private String accountbalance;
    public String name_of_user;
    public String username;
    private String[] other_users;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finances);
        getActionBar().setTitle("Finances");


        accountbalance=getAccountBalance();

        //Fills TextView with Account Balance
        //TextView tv = (TextView) findViewByID(R.id.AccountBalance);
    }



    public void addTransaction(View v){
     Intent i= new Intent(this, TransactionActivity.class);
     startActivity(i);

    }

    public void generateBill(View v){
    //Generates entire bill in pdf format

    }


    public String getAccountBalance() {

        String ab="";

        //Convert credit amount to double

        //Convert charge amount to double

        //add amounts together

        //Convert back to string and return

        return ab;
    }
}
