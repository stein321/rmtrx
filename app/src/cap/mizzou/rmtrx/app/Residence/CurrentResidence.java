package cap.mizzou.rmtrx.app.Residence;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import cap.mizzou.rmtrx.app.R;
import cap.mizzou.rmtrx.app.User_setup.ResidenceCreationInterface;
import cap.mizzou.rmtrx.app.User_setup.UserInfo;
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
    private UserInfo userInfo;

//    private ListView nameList;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.residence_information);
        getActionBar().setTitle("Residence Information");
        Context context=getApplicationContext();
        userInfo=new UserInfo(context);

        setInfoOnPage();
    }
    public void showCode() {
                 String code_to_display=generateCode();
                 TextView show_code=(TextView)findViewById(R.id.code);

                show_code.setText(code_to_display);
        RestAdapter restAdapter =
                new RestAdapter.Builder().setServer("http://powerful-thicket-5732.herokuapp.com/").build();
        ResidenceCreationInterface ri= restAdapter.create(ResidenceCreationInterface.class);

        ri.saveResidenceCode(code_to_display,userInfo.getResidenceId(), new Callback<Code>() {
            @Override
            public void success(Code code, Response response) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }
    private void setInfoOnPage() {
        TextView showNameOfUser=(TextView)findViewById(R.id.user_name);   //hard code for now
        TextView showNameOfResidence=(TextView)findViewById(R.id.address);
//        TextView show_code_of_residence=(TextView)findViewById(R.id.code);
        TextView show_number_of_spots_left_in_residence=(TextView)findViewById(R.id.number_of_people_left);

        showNameOfUser.setText(userInfo.getFirstName() + " " + userInfo.getLastName());
        showNameOfResidence.setText(userInfo.getResidenceName());
    }

    private String generateCode() {
       String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
       int maxLength = alphabet.length();

        String code = "";

        Random r = new Random();

        for (int i = 0; i < 6; i++) {
         code = code.concat(String.valueOf(alphabet.charAt(r.nextInt(maxLength))));
           }

        return code;
        }
}
