package cap.mizzou.rmtrx.app.Finances;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import cap.mizzou.rmtrx.app.R;




/**
 * Created with IntelliJ IDEA.
 * User: Desi
 * Date: 10/20/13
 * Time: 2:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class TransactionActivity extends Activity {

   //shared pref.

    private FinancesDB datasource;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction);
        getActionBar().setTitle("Add Transaction");


     // datasource = new FinancesDB(this);
     // datasource.open();

    }


    public void sendTransaction(View v){
        //Grabs credit amount
        EditText amountText = (EditText) findViewById(R.id.TransactionAmount);

        //Converts
              Double amount=Double.parseDouble(amountText.getText().toString());
        //Send transaction info to record creation method
       datasource.createTransactionRecord("Des", amount);




    }


}
