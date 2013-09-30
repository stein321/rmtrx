package cap.mizzou.rmtrx.app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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

    private String login_name;  //just for printing to log
    private String password; //just for printing to log

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        SharedPreferences user_name = getSharedPreferences();
        setContentView(R.layout.activity_main);
        getActionBar().setTitle("Login");
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void sendLoginInfo(View view) {
        //saved to shared preferences
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        EditText login_name_text = (EditText) findViewById(R.id.login_name);
        EditText p_word_text = (EditText) findViewById(R.id.p_word);

        String login_to_add = login_name_text.getText().toString();
        String p_word_to_add = p_word_text.getText().toString();

        editor.putString("login_name", login_to_add);
        editor.putString("p_word", p_word_to_add);
        editor.commit();

        login_name = pref.getString("login_name", null);
        password = pref.getString("p_word", null);

        Log.d("test-login", login_name);
        Log.d("test-pass", password);
        return;
    }

}
