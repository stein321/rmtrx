package cap.mizzou.rmtrx.app.DataAccess;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import cap.mizzou.rmtrx.app.R;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 11/8/13
 * Time: 9:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShowResidentsActivity extends ListActivity {
    private ResidentDataSource data;

   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.resident_list);
       data = new ResidentDataSource(this);
       data.open();

       List<Resident> values=data.getAllResidents();

       ArrayAdapter<Resident> adapter=new ArrayAdapter<Resident>(this,android.R.layout.simple_list_item_1,values);
       setListAdapter(adapter);
    }

    @Override
    protected void onResume() {
        data.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        data.close();
        super.onPause();
    }


}
