package cap.mizzou.rmtrx.app.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cap.mizzou.rmtrx.app.LoginActivity;
import cap.mizzou.rmtrx.app.Messages.MessagesActivity;
import cap.mizzou.rmtrx.app.R;
import cap.mizzou.rmtrx.app.SQLite.TestDatabaseActivity;
import cap.mizzou.rmtrx.app.grocery.GroceryActivity;
import cap.mizzou.rmtrx.core.ui.BaseListFragment;
import com.squareup.otto.Bus;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Javadoc needs to be done.
 */
public class HomeFragment extends BaseListFragment {

    public static final String TAG = HomeFragment.class.getName();

    private static final String STATE_LAST_CHECKED_ITEM = "lastCheckedItem";

    private List<RmtrxActivity> activities;

    private int lastCheckedItem;

    @Inject
    Bus bus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activities = getActivities();
        lastCheckedItem = ListView.INVALID_POSITION;
        if (savedInstanceState != null) {
            lastCheckedItem = savedInstanceState.getInt(STATE_LAST_CHECKED_ITEM, ListView.INVALID_POSITION);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new RmtrxAdapter(getActivity(), activities));

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onListItemClick(ListView l,
                                View v,
                                int position,
                                long id) {
        if (lastCheckedItem != ListView.INVALID_POSITION && lastCheckedItem == position) {
            return;
        }

        lastCheckedItem = l.getCheckedItemPosition();

        RmtrxActivity item = (RmtrxActivity) l.getItemAtPosition(position);
        bus.post(new ActivitySelectedEvent(item));

    }

    //this lists all the menu items on the dashboard
    public List<RmtrxActivity> getActivities() {
        List<RmtrxActivity> activitiesList = new ArrayList<RmtrxActivity>();
        activitiesList.add(new RmtrxActivity(R.string.grocery_list_activity_name, GroceryActivity.class));
        activitiesList.add(new RmtrxActivity(R.string.messages_activity_name, MessagesActivity.class));
        activitiesList.add(new RmtrxActivity(R.string.login_activity_name, LoginActivity.class));
        activitiesList.add(new RmtrxActivity(R.string.db_attempt_name, TestDatabaseActivity.class));
/*        activitiesList.add(new RmtrxActivity(R.string.action_settings, SettingsActivity.class));*/

        return activitiesList;
    }

    //

    /**
     * Adapter for this activity that will hold possible
     */
    private static final class RmtrxAdapter extends ArrayAdapter<RmtrxActivity> {

        Context context;

        private RmtrxAdapter(Context context,
                             List<RmtrxActivity> accounts) {
            super(context, android.R.layout.simple_list_item_1, accounts);
            this.context = context;
        }

        @Override
        public View getView(int position,
                            View convertView,
                            ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(android.R.id.text1);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            RmtrxActivity rmtrxActivity = getItem(position);


            holder.name.setText(context.getString(rmtrxActivity.resourceId));


            return convertView;
        }
    }

    static class ViewHolder {

        TextView name;
    }

    public final class RmtrxActivity {

        int resourceId;
        Class<? extends Activity> activityClass;

        public RmtrxActivity(int resourceId,
                             Class<? extends Activity> activityClass) {
            this.resourceId = resourceId;
            this.activityClass = activityClass;
        }
    }
}
