package cap.mizzou.rmtrx.app.BulletinBoard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import cap.mizzou.rmtrx.app.R;

import java.util.ArrayList;


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
    ArrayList <String> posts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bulletin_board);
        getActionBar().setTitle("Bulletin Board");

        // Get the reference of ListViewAnimals
        ListView postlistview=(ListView)findViewById(R.id.bblistview);

        posts= new ArrayList<String>();

          //This won't work until you actually pass Extra info with the intent.
//        //Receives data passed with intent
          Intent intent = getIntent();

          //String message = intent.getExtras("message");
//        Bundle extras = getIntent().getExtras();
//        String title = extras.getString("title");
//        String message = extras.getString("message");
//
//        //Adds messages to ArrayList
         //posts.add(message);


        //Create Adapter
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, posts);
        // Set Adapter
        postlistview.setAdapter(arrayAdapter);

        // register onClickListener to handle click events on each item
        postlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {

                String selectedpost = posts.get(position);
                Toast.makeText(getApplicationContext(), "Post Selected: " + selectedpost, Toast.LENGTH_LONG).show();

                Intent display= new Intent(getApplicationContext(), DisplayBBPost.class);
                display.putExtra("message", selectedpost);
                startActivity(display);
            }
        });



    }

    public void addBBPost(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, AddBBPost.class);
        startActivity(intent);
    }
}






