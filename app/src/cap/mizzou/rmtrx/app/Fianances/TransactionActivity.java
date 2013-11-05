package cap.mizzou.rmtrx.app.Fianances;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import cap.mizzou.rmtrx.app.R;
import cap.mizzou.rmtrx.app.Residence.CurrentResidence;


/**
 * Created with IntelliJ IDEA.
 * User: Desi
 * Date: 10/20/13
 * Time: 2:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class TransactionActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction);
        getActionBar().setTitle("Add Transaction");

    }


    public void addCredit(View v){
        //Grabs credit amount
        EditText amount = (EditText) findViewById(R.id.CreditAmount);

        //Decide which roommate to credit

        //Send credit info to database (roommate specific table)


    }

    public void addCharge(View v){
         //Grabs charge amount
        EditText amount  = (EditText) findViewById(R.id.ChargeAmount);

        //Decide which roommate to charge

        //Send charge info to database (roommate specific table)
    }
}
