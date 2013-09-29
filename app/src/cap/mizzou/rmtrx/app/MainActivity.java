package cap.mizzou.rmtrx.app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 9/21/13
 * Time: 8:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainActivity extends FragmentActivity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private SharedPreferences user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences user_name = getSharedPreferences();
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }


}
