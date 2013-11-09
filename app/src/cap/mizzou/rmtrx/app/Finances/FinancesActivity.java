package cap.mizzou.rmtrx.app.Finances;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import cap.mizzou.rmtrx.app.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.pdf.*;
import com.itextpdf.text.DocumentException;
import android.util.*;
import org.w3c.dom.*;


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
    //private FinancesDB datasource;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finances);
        getActionBar().setTitle("Finances");

      //  datasource = new FinancesDB(this);
       // datasource.open();
       // accountbalance=getAccountBalance();

        //Fills TextView with Account Balance
        //TextView ab = (TextView) findViewByID(R.id.AccountBalance);
    }



    public void addTransaction(View v){
     Intent i= new Intent(this, TransactionActivity.class);
     startActivity(i);

    }

    public void generateBill(View v){
    //Generates entire bill in pdf format

    }
    public String getAccountBalance() {

        double sum=0;

        List<Double> list=new ArrayList<Double>();
        //Retrieves list of
       // list= datasource.getTransactionRecords("Des");

        for (int i = 0; i < list.size(); i++) {
              sum=sum + list.get(i);
        }

        String ab= String.valueOf(sum);
        return ab;
    }
}



