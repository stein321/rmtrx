package cap.mizzou.rmtrx.app.Residence;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cap.mizzou.rmtrx.app.R;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 10/10/13
 * Time: 10:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class CurrentResidence extends Activity {
//    private SharedPreferences residence_info=getApplicationContext().getSharedPreferences("MyPref", 0);;

    public String name_of_user;
    public String username;
    public String address;
    public int number_of_users=1;
    private ListView nameList;
    private String[] other_users;
    private String code;
//    public User[] other_users;     //need to create User object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.residence_information);
        getActionBar().setTitle("Residence Information");

        grabInformationFromSharedPreferencesAndSetAttributes();
        setInfoOnPage();
    }

    private void setInfoOnPage() {
        TextView show_name_of_user=(TextView)findViewById(R.id.user_name);   //hard code for now
        TextView show_address_of_residence=(TextView)findViewById(R.id.address);
        TextView show_code_of_residence=(TextView)findViewById(R.id.code);
        TextView show_number_of_spots_left_in_residence=(TextView)findViewById(R.id.number_of_people_left);

        String number_of_users_string=String.valueOf(number_of_users);
        show_number_of_spots_left_in_residence.setText(number_of_users_string);
        show_name_of_user.setText(name_of_user);
        show_address_of_residence.setText(address);
        show_code_of_residence.setText(code);
        nameList=(ListView)findViewById(R.id.nameList);

        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, other_users);
        nameList.setAdapter(arrayAdapter);
    }
    public void grabInformationFromSharedPreferencesAndSetAttributes() {
//               name_of_user=residence_info.getString("name_of_user",null);
//               username=residence_info.getString("username",null);
//               address=residence_info.getString("address",null);
//               number_of_users_left=residence_info.getInt("users_left",0);
        code="01525";
        name_of_user="Ben Stein";
        address="509 S 5th St";
        username="ben";
        number_of_users=4;
        other_users=new String[number_of_users-1];
//        sample array just to do listview
        other_users[0]="mike";
        other_users[1]="Des";
        other_users[2]="Alec";





    }
}
