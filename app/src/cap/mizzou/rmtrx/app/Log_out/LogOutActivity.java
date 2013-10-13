package cap.mizzou.rmtrx.app.Log_out;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import cap.mizzou.rmtrx.app.ui.HomeActivity;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 10/6/13
 * Time: 1:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class LogOutActivity extends Activity {  //make sure to delete all shared preferences and probably all TestDbActivity Data

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        this.removeLocalData(editor);
        this.setLoggedInStatusToLoggedOut(editor);
        editor.putBoolean("logged_in_status_yo", false);
        editor.commit();      //only commit if everything works


//        setContentView(R.layout.registration_page);
//        getActionBar().setTitle("Login");
//        getActionBar().setDisplayHomeAsUpEnabled(true);
        Intent myIntent = new Intent(this, HomeActivity.class);
        myIntent.putExtra(EXTRA_MESSAGE, "Logged Out");
        startActivity(myIntent);


    }

    private SharedPreferences.Editor removeLocalData(SharedPreferences.Editor editor) {
        editor.remove("name");
        editor.remove("email");
        return editor;
    }

    private SharedPreferences.Editor setLoggedInStatusToLoggedOut(SharedPreferences.Editor editor) {
        editor.putBoolean("logged_in_status_yo", false);
        return editor;
    }

}
