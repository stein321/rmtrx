package cap.mizzou.rmtrx.app.BulletinBoard;

import android.app.Activity;
import android.os.Bundle;
import cap.mizzou.rmtrx.app.R;
import android.view.View;
import android.widget.*;
import android.content.Intent;
import cap.mizzou.rmtrx.app.User_setup.UserInfo;

/**
 * Created with IntelliJ IDEA.
 * User: Desi
 * Date: 11/13/13
 * Time: 11:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddBBPostActivity extends Activity {
    BulletinBoardDb datasource;
    UserInfo userinfo;
    String userid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post);
        getActionBar().setTitle("Bulletin Board");
        getActionBar().setDisplayHomeAsUpEnabled(true);

        datasource = new BulletinBoardDb(this);
        datasource.open();


        userinfo= new UserInfo(this);
        userid=userinfo.getId();


    }


    public void sendPost(View v){

        Post comment;
        EditText title = (EditText) findViewById(R.id.title);
        EditText details = (EditText) findViewById(R.id.additional_details);
        String postTitle = title.getText().toString();
        String postDetails = details.getText().toString();

        // Save the new comment to the database
        comment = datasource.createPost("37536483758", postTitle, postDetails);


        Intent goback= new Intent(this, BulletinBoardActivity.class);
        startActivity(goback);


    }

}