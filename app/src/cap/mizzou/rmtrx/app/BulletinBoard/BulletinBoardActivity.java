package cap.mizzou.rmtrx.app.BulletinBoard;

import android.app.Activity;
import android.content.Intent;
import cap.mizzou.rmtrx.app.R;
import android.os.Bundle;
import java.util.*;
import android.widget.*;
import android.content.*;
import android.view.View;
import android.widget.AdapterView.*;

/**
 * Created with IntelliJ IDEA.
 * User: Desi
 * Date: 10/12/13
 * Time: 5:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class BulletinBoardActivity extends Activity {


    ListView listView ;
    // Defined Array values to show in ListView
    ArrayList <String> values;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bulletin_board);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.bblistview);



        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();

            }

        });
    }




    public void addBBPost(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, AddBBPost.class);
        startActivity(intent);
    }




    Intent post = getIntent();
    String message = post.getStringExtra(AddBBPost.EXTRA_MESSAGE);





}






