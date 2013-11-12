package cap.mizzou.rmtrx.app.Finances;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import cap.mizzou.rmtrx.app.R;
import android.app.ListActivity;

import java.util.List;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finances);
        getActionBar().setTitle("Finances");



        datasource = new FinanceDb(this);
        datasource.open();




         accountbalance=getAccountBalance("0231");
         abText= String.valueOf(accountbalance);

        TextView tv= (TextView)findViewById(R.id.account_balance);

        tv.setText(abText);

}
    public void addTransaction(View v){
        @SuppressWarnings("unchecked")

        Transaction transaction;


            EditText amountText = (EditText) findViewById(R.id.transaction_amount);
            //Converts text to double
            Double amount=Double.parseDouble(amountText.getText().toString());
            //Sends transaction info to record creation method
            transaction=datasource.createTransaction("0231", amount);

        //add toast message
    }

    public double getAccountBalance(String userid){
        double ab=0;
        List<Transaction> values = datasource.getAllTransactions(userid);

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



