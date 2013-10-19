package cap.mizzou.rmtrx.app.Residence;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cap.mizzou.rmtrx.app.R;
import cap.mizzou.rmtrx.app.User_setup.ResidenceCreationInterface;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 10/10/13
 * Time: 10:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class CurrentResidence extends Activity {
    private SharedPreferences residence_info=getApplicationContext().getSharedPreferences("MyPref", 0);;

//    public String residence_id;
//    public String name_of_user;
//    public String username;
//    public String address;
//    public int number_of_users=1;
////    private ListView nameList;
//    private String[] other_users;
//    private String code;
//    public User[] other_users;     //need to create User object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.residence_information);
//        getActionBar().setTitle("Residence Information");

//        grabInformationFromSharedPreferencesAndSetAttributes();
//        setInfoOnPage();
//        residence_id="asdasdasdasdasd";//residence_info.getString("residence_id",null);
    }
//    public void showCode() {
//                 String code_to_display=generateCode();
//                 TextView show_code=(TextView)findViewById(R.id.code);
//
//                show_code.setText(code_to_display);
//        RestAdapter restAdapter =
//                new RestAdapter.Builder().setServer("http://powerful-thicket-5732.herokuapp.com/").build();
//        ResidenceCreationInterface ri= restAdapter.create(ResidenceCreationInterface.class);
//
//        ri.saveResidenceCode(code_to_display,residence_id, new Callback<Code>() {
//            @Override
//            public void success(Code code, Response response) {
//                //To change body of implemented methods use File | Settings | File Templates.
//            }
//
//            @Override
//            public void failure(RetrofitError retrofitError) {
//                //To change body of implemented methods use File | Settings | File Templates.
//            }
//        });
//    }
//    private void setInfoOnPage() {
//        TextView show_name_of_user=(TextView)findViewById(R.id.user_name);   //hard code for now
//        TextView show_address_of_residence=(TextView)findViewById(R.id.address);
////        TextView show_code_of_residence=(TextView)findViewById(R.id.code);
//        TextView show_number_of_spots_left_in_residence=(TextView)findViewById(R.id.number_of_people_left);
//
//        String number_of_users_string=String.valueOf(number_of_users);
//        show_number_of_spots_left_in_residence.setText(number_of_users_string);
//        show_name_of_user.setText(name_of_user);
//        show_address_of_residence.setText(address);
////        show_code_of_residence.setText(code);
////        nameList=(ListView)findViewById(R.id.nameList);
//
////        ArrayAdapter<String> arrayAdapter =
////                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, other_users);
////        nameList.setAdapter(arrayAdapter);
//    }
//    public void grabInformationFromSharedPreferencesAndSetAttributes() {
////               name_of_user=residence_info.getString("name_of_user",null);
////               username=residence_info.getString("username",null);
////               address=residence_info.getString("address",null);
////               number_of_users_left=residence_info.getInt("users_left",0);
//
//        SharedPreferences user_values=getApplicationContext().getSharedPreferences("MyPref", 0);
//        code="1234";
//        name_of_user=user_values.getString("email",null);
//        address= user_values.getString("residence_name","509 S 5th St");
//        username=user_values.getString("email",null);
//        //will be a database call
//        number_of_users=4;
//
//        other_users=new String[number_of_users-1];
////        sample array just to do listview
//        other_users[0]="mike";
//        other_users[1]="Des";
//        other_users[2]="Alec";
//
//
//
//
//
//    }
//    private String generateCode() {
//       String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//       int maxLength = alphabet.length();
//
//        String code = "";
//
//        Random r = new Random();
//
//        for (int i = 0; i < 6; i++) {
//         code = code.concat(String.valueOf(alphabet.charAt(r.nextInt(maxLength))));
//           }
//
//        return code;
//        }
}
