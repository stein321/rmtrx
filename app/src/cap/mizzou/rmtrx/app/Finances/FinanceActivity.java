package cap.mizzou.rmtrx.app.Finances;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import cap.mizzou.rmtrx.app.BulletinBoard.BulletinBoardDb;
import cap.mizzou.rmtrx.app.BulletinBoard.Post;
import cap.mizzou.rmtrx.app.R;
import android.app.Activity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Desi
 * Date: 11/12/13
 * Time: 4:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class FinanceActivity extends Activity {

    //private FinanceDb datasource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finances);
        getActionBar().setTitle("Finances");
        getActionBar().setDisplayHomeAsUpEnabled(true);


       // datasource = new FinanceDb(this);
        //datasource.open();

        //List<Transaction> values = datasource.getAllTransactions();

        // Use the SimpleCursorAdapter to show the
        // elements in a ListView
        //ArrayAdapter<Transaction> adapter = new ArrayAdapter<Transaction>(this,
               // android.R.layout.simple_list_item_1, values);
        //setListAdapter(adapter);




}
    public void addTransaction(View v){
        Intent i= new Intent(this, TransactionActivity.class);
        startActivity(i);

    }

}

