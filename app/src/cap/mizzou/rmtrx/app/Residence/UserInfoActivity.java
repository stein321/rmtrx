package cap.mizzou.rmtrx.app.Residence;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import cap.mizzou.rmtrx.app.DataAccess.Resident;
import cap.mizzou.rmtrx.app.DataAccess.ResidentDataSource;
import cap.mizzou.rmtrx.app.DataAccess.ShowResidentsActivity;
import cap.mizzou.rmtrx.app.R;
import cap.mizzou.rmtrx.app.User_setup.ResidenceCreationInterface;
import cap.mizzou.rmtrx.app.User_setup.UserInfo;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.Random;
    //TODO:Make info display correctly
/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 10/10/13
 * Time: 10:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserInfoActivity extends Activity {
    private UserInfo userInfo;
    private ResidentDataSource data;
    private String code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_page);
        getActionBar().setTitle("Residence Information");
        Context context=getApplicationContext();
        setUserInfo(new UserInfo(context));
        setData(new ResidentDataSource(this));
        getData().open();
//        userInfo.dumpPrefValues();


        setInfoOnPage();
    }
    //need to be connected to the in
    public void generateCode(View view) {
                 setCode(generateCode());

        RestAdapter restAdapter =
                new RestAdapter.Builder().setServer("http://powerful-thicket-5732.herokuapp.com/").build();
        ResidenceCreationInterface ri= restAdapter.create(ResidenceCreationInterface.class);

        ri.saveResidenceCode(getUserInfo().getResidenceId(),getCode(), new Callback<Code>() {
            @Override
            public void success(Code code, Response response) {
                setNewCodeOnPage();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
            }
        });
    }

    private void setNewCodeOnPage() {
        TextView show_code=(TextView)findViewById(R.id.code);

        show_code.setText(getCode());
    }
    private void setInfoOnPage() {
        TextView showNameOfUser=(TextView)findViewById(R.id.user_name);   //hard code for now
        TextView showNameOfResidence=(TextView)findViewById(R.id.residence);

        showNameOfUser.setText(getUserInfo().getFirstName() + " " + getUserInfo().getLastName());
        showNameOfResidence.setText(getUserInfo().getResidenceName());
    }
    public void showResidentsList(View view) {
        Intent showList=new Intent(this, ShowResidentsActivity.class);
        startActivity(showList);
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
    public void truncateTable(View view) {
        getData().truncate();
        this.deleteDatabase("grocery.db");
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public ResidentDataSource getData() {
        return data;
    }

    public void setData(ResidentDataSource data) {
        this.data = data;
    }


}
