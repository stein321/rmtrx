package cap.mizzou.rmtrx.app.Finances;

import android.os.Bundle;
import android.widget.TableLayout;
import cap.mizzou.rmtrx.app.R;
import android.app.Activity;
import cap.mizzou.rmtrx.app.User_setup.UserInfo;

import java.util.List;
import android.widget.*;
import android.util.*;
import android.view.ViewGroup.LayoutParams;

/**
 * Created with IntelliJ IDEA.
 * User: Desi
 * Date: 11/15/13
 * Time: 3:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class GenerateBillActivity extends Activity {
    private FinanceDb datasource;
    private UserInfo userinfo;
    private String userid;
    TableLayout bill_table;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill);

        userinfo= new UserInfo(this);
        userid=userinfo.getId();





        datasource = new FinanceDb(this);
        datasource.open();

        List<Transaction> values = datasource.getAllTransactions(userid);

        ScrollView sv = new ScrollView(this);

        TableLayout ll=new TableLayout(this);

         String [] columnName= new String[3];
        columnName[0]="Date";
        columnName[1]="Amount";
        columnName[2]="Nature";
        for(int i=0;i<values.size()+1;i++) {
            TableRow tbrow=new TableRow(this);
            tbrow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
           TextView tv1=new TextView(this);
            TextView tv2=new TextView(this);
            TextView tv3=new TextView(this);
            tv1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            tv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            tv3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));


           /*
            for(int j=0;j<3;j++) {
                TextView tv1=new TextView(this);

                String s1 = Integer.toString(i);
                String s2 = Integer.toString(j);
                String s3 = s1+s2;
                int id = Integer.parseInt(s3);
                tv1.setId(id);
                */
                if(i==0){
                tv1.setText(columnName[0]);
                tbrow.addView(tv1);
                    tv2.setText(columnName[1]);
                    tbrow.addView(tv2);
                    tv3.setText(columnName[2]);
                    tbrow.addView(tv3);
                }
                else{
                    tv1.setText(values.get(i-1).getDate());
                    tbrow.addView(tv1);
                    tv2.setText(String.valueOf(values.get(i-1).getAmount()));
                    tbrow.addView(tv2);
                    tv3.setText(values.get(i-1).getNature());
                    tbrow.addView(tv3);
                }
           // }
            ll.addView(tbrow);
        }


        setContentView(ll);
    }

}