package cap.mizzou.rmtrx.app.grocery;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cap.mizzou.rmtrx.app.R;
import cap.mizzou.rmtrx.core.ui.BaseFragmentActivity;

public class GroceryActivity extends Activity {
    protected TaskDB db;
    List<Task> list;
    MyAdapter adapt;
=======
import android.app.Activity;
import android.os.Bundle;
import cap.mizzou.rmtrx.app.R;

/**
 * Javadoc needed
 */
public class GroceryActivity extends Activity {
>>>>>>> 604866c0e2befea9c12029da170e58f875c6b030

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD

        //getSupportActionBar().setTitle("Grocery List");

        setContentView(R.layout.view_task);
        db = new TaskDB(this);
        list = db.getAllTasks();
        adapt = new MyAdapter(this, R.layout.list_inner_view, list);
        ListView listTask = (ListView) findViewById(R.id.listView1);
        listTask.setAdapter(adapt);
=======
        getActionBar().setTitle("Grocery List");
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.article_view);
>>>>>>> 604866c0e2befea9c12029da170e58f875c6b030
    }

    public void addTaskNow(View v) {
        EditText t = (EditText) findViewById(R.id.editText1);
        String s = t.getText().toString();
        if (s.equalsIgnoreCase("")) {
            Toast.makeText(this, "enter the task description first",
                    Toast.LENGTH_LONG);
        } else {
            Task task = new Task(s, 0);
            db.addTask(task);
            Log.d("task", "data added");
            t.setText("");
            adapt.add(task);
            adapt.notifyDataSetChanged();
        }

    }

    private class MyAdapter extends ArrayAdapter<Task> {

        Context context;
        List<Task> taskList = new ArrayList<Task>();
        int layoutResourceId;

        public MyAdapter(Context context, int layoutResourceId,
                         List<Task> objects) {
            super(context, layoutResourceId, objects);
            this.layoutResourceId = layoutResourceId;
            this.taskList = objects;
            this.context = context;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CheckBox chk = null;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_inner_view,
                        parent, false);
                chk = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(chk);

                chk.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        Task changeTask = (Task) cb.getTag();
                        changeTask.setStatus(cb.isChecked() == true ? 1 : 0);
                        db.updateTask(changeTask);
                        Toast.makeText(
                                getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() + " is "
                                        + cb.isChecked(), Toast.LENGTH_LONG)
                                .show();
                    }

                });
            } else {
                chk = (CheckBox) convertView.getTag();
            }
            Task current = taskList.get(position);
            chk.setText(current.getTaskName());
            chk.setChecked(current.getStatus() == 1 ? true : false);
            chk.setTag(current);
            Log.d("listener", String.valueOf(current.getId()));
            return convertView;
        }

    }

}
