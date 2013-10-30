package cap.mizzou.rmtrx.app.Finances;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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

    private FinancesDB datasource;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction);
        getActionBar().setTitle("Add Transaction");


        datasource = new FinancesDB(this);
        datasource.open();

    }


    public void addCredit(View v){
        //Grabs credit amount
        EditText amount = (EditText) findViewById(R.id.CreditAmount);

        //Decide which roommate to credit

        //Send credit info to database (roommate specific table)
        datasource.createCreditRecord("Alice", "100.00");


        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(this, FinancesActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Build notification
        // Actions are just fake
        Notification noti = new Notification.Builder(this)
                .setContentTitle("New Credit")
                .setContentText("$30")
                .setContentIntent(pIntent)
                .addAction(1, "And more", pIntent).build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, noti);

    }

    public void addCharge(View v){
         //Grabs charge amount
        EditText amount  = (EditText) findViewById(R.id.ChargeAmount);

        //Decide which roommate to charge

        //Send charge info to database (roommate specific table)


        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(this, FinancesActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Build notification
        // Actions are just fake
        Notification noti = new Notification.Builder(this)
                .setContentTitle("New Charge From" + "Alice")
                .setContentText("You were charged $10")
                .setContentIntent(pIntent)
                .addAction(1, "And more", pIntent).build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, noti);

    }
}
