package cap.mizzou.rmtrx.app.Finances;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import cap.mizzou.rmtrx.app.R;
import java.util.Date;
import android.widget.ArrayAdapter;
import android.app.ListActivity;




/**
 * Created with IntelliJ IDEA.
 * User: Desi
 * Date: 10/20/13
 * Time: 2:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class TransactionActivity extends Activity {

   //shared pref.

    private FinanceDb datasource;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction);
        getActionBar().setTitle("Add Transaction");


       // datasource = new FinanceDb(this);
       // datasource.open();

    }


    public void sendTransaction(View v){


        //Grabs transaction amount
        EditText amountText = (EditText) findViewById(R.id.TransactionAmount);

        //Converts text to double
        Double amount=Double.parseDouble(amountText.getText().toString());
        //Sends transaction info to record creation method
        Transaction trans = datasource.createTransaction("0231",amount);


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



