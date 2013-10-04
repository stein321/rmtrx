package cap.mizzou.rmtrx.app.grocery;

import android.os.Bundle;
import cap.mizzou.rmtrx.app.R;
import cap.mizzou.rmtrx.core.ui.BaseFragmentActivity;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import android.view.View;

import android.widget.EditText;
import android.widget.ListView;

import android.widget.Toast;



/**
 * Javadoc needed
 */

public class GroceryActivity extends BaseFragmentActivity {
    protected TaskDB db;
    List<Task> list;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Grocery List");

        setContentView(R.layout.view_task);


    }


    public void addTaskNow(View v) {
        EditText t = (EditText) findViewById(R.id.editText1);
        String s = t.getText().toString();
        if (s.equalsIgnoreCase("")) {
            Toast.makeText(this, "enter task description",
                    Toast.LENGTH_LONG);
        } else {
            Task task = new Task(s, 0);
            db.addTask(task);
            Log.d("tasks", "data added");
            t.setText("");
    //        adapt.add(task);
    //        adapt.notifyDataSetChanged();
}
}
}