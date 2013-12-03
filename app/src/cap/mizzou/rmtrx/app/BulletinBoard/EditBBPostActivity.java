package cap.mizzou.rmtrx.app.BulletinBoard;

import android.os.Bundle;
import android.view.View;
import cap.mizzou.rmtrx.app.R;
import cap.mizzou.rmtrx.app.User_setup.UserInfo;
import android.app.Activity;
import cap.mizzou.rmtrx.app.BulletinBoard.BulletinBoardDb;
import android.widget.*;
import android.content.Intent;
import android.content.ContentValues;

/**
 * Created with IntelliJ IDEA.
 * User: Desi
 * Date: 11/15/13
 * Time: 2:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class EditBBPostActivity extends Activity{
    BulletinBoardDb datasource;
    Post postinfo;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post);
        getActionBar().setTitle("Bulletin Board");
        getActionBar().setDisplayHomeAsUpEnabled(true);

        datasource = new BulletinBoardDb(this);
        datasource.open();

        Intent receive = getIntent();
        postinfo = (Post)receive.getSerializableExtra("postToEdit");

        EditText titleText = (EditText)findViewById(R.id.title);
        EditText detailsText = (EditText)findViewById(R.id.additional_details);
        //Fills editTextViews with saved information
        titleText.setText(postinfo.getPostTitle(), TextView.BufferType.EDITABLE);
        detailsText.setText(postinfo.getPostDetails(), TextView.BufferType.EDITABLE);

    }

    public void sendPost(View v){

        boolean change=false;
        ContentValues cv = new ContentValues();
        EditText titleText = (EditText)findViewById(R.id.title);
        EditText detailsText = (EditText)findViewById(R.id.additional_details);

        String postTitle = titleText.getText().toString();
        String postDetails = detailsText.getText().toString();

         if(!(postTitle.equals(postinfo.getPostTitle()))){
             postinfo.setPostTitle(postTitle);


             cv.put("post", postinfo.getPostTitle());
             change=true;

         }

        if(postDetails.equals(postinfo.getPostDetails())){
            postinfo.setPostDetails(postDetails);
            cv.put("details", postinfo.getPostDetails());

            if(change=false){
             change=true;
            }


        }
        if(change=true){
        datasource.editPost(cv, postinfo.getId());
        }

        Intent goback= new Intent(this, BulletinBoardActivity.class);
        startActivity(goback);
    }


    }

