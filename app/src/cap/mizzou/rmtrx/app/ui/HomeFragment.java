package cap.mizzou.rmtrx.app.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cap.mizzou.rmtrx.app.BulletinBoard.BulletinBoardActivity;
import cap.mizzou.rmtrx.app.Finances.FinanceActivity;
import cap.mizzou.rmtrx.app.LogOut.LogOutActivity;
import cap.mizzou.rmtrx.app.Messages.MessagesActivity;
import cap.mizzou.rmtrx.app.R;
import cap.mizzou.rmtrx.app.Residence.UserInfoActivity;
import cap.mizzou.rmtrx.app.grocery.GroceryActivity;
import cap.mizzou.rmtrx.core.ui.BaseListFragment;
import com.squareup.otto.Bus;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

//import cap.mizzou.rmtrx.app.SQLite.TestDatabaseActivity;

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
        activitiesList.add(new RmtrxActivity(R.string.bulletin_board_activity_name, BulletinBoardActivity.class));
        activitiesList.add(new RmtrxActivity(R.string.finances_activity_name, FinanceActivity.class));
        activitiesList.add(new RmtrxActivity(R.string.messages_activity_name, MessagesActivity.class));
        activitiesList.add(new RmtrxActivity(R.string.logout_name, LogOutActivity.class));
        activitiesList.add(new RmtrxActivity(R.string.residence_info, UserInfoActivity.class));
        //  activitiesList.add(new RmtrxActivity(R.string.registration_activity_name, RegistrationActivity.class));
/*        activitiesList.add(new RmtrxActivity(R.string.action_settings, SettingsActivity.class));*/

        return activitiesList;
    }

    //

    /**
     * Adapter for this activity that will hold possible
     */
    public static final class RmtrxAdapter extends ArrayAdapter<RmtrxActivity> {

        Context context;
        String[] values;
        List<RmtrxActivity> activities;

        private RmtrxAdapter(Context context,
                             List<RmtrxActivity> accounts) {
            super(context, R.layout.dashboard, accounts);
            this.context = context;
            this.values = values;
            this.activities = accounts;
        }

        @Override
        public View getView(int position,
                            View convertView,
                            ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.dashboard, parent, false);
                holder = new ViewHolder();
//icon
                TextView textView = (TextView) convertView.findViewById(R.id.label);
                ImageView imageView = (ImageView) convertView.findViewById(R.id.logo);

//


                holder.name = (TextView) convertView.findViewById(android.R.id.text1);
                convertView.setTag(holder);


            //icon

            //    ArrayList.indexOf("Grocery List");
            //    activitiesList.contains("Grocery List");




               // Integer s =  ArrayList.indexOf("Grocery List");
                //        activitiesList.get(position);
               // System.out.println(s);

                //

                RmtrxActivity activity = getActivityById(position);



                if (activitiesList.contains(R.string.grocery_list_activity_name)) {
                    imageView.setImageResource(R.drawable.ic_check);
                }  else if (activitiesList.contains(R.string.bulletin_board_activity_name)) {
                    imageView.setImageResource(R.drawable.ic_picture);
                }  else if (activitiesList.contains(R.string.residence_info)) {
                    imageView.setImageResource(R.drawable.ic_profile);
                }  else if (activitiesList.contains(R.string.messages_activity_name)) {
                    imageView.setImageResource(R.drawable.ic_speech);
                }  else if (activitiesList.contains(R.string.logout_name)) {
                    imageView.setImageResource(R.drawable.ic_unlock);
                }  else if (activitiesList.contains(R.string.finances_activity_name)) {
                    imageView.setImageResource(R.drawable.ic_charge);
                }  else {
                    imageView.setImageResource(R.drawable.ic_construct);
                }



                /*if (s.equals("Grocery List")) {
                    imageView.setImageResource(R.drawable.ic_check);
                }  else if (s.equals("Bulletin Board")) {
                    imageView.setImageResource(R.drawable.ic_picture);
                }  else if (s.equals("Profile")) {
                    imageView.setImageResource(R.drawable.ic_profile);
                }  else if (s.equals("Messages")) {
                    imageView.setImageResource(R.drawable.ic_speech);
                }  else if (s.equals("Logout")) {
                    imageView.setImageResource(R.drawable.ic_unlock);
                }  else if (s.equals("Tasks")) {
                    imageView.setImageResource(R.drawable.ic_clock);
                }  else if (s.equals("Finances")) {
                    imageView.setImageResource(R.drawable.ic_charge);
                }  else if (s.equals("Location")) {
                    imageView.setImageResource(R.drawable.ic_home);
                } else {
                    imageView.setImageResource(R.drawable.ic_construct);
                }*/



            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            RmtrxActivity rmtrxActivity = getItem(position);


            holder.name.setText(context.getString(rmtrxActivity.resourceId));



            return convertView;
        }

        public RmtrxActivity getActivityById(int id) {
            return this.activities.get(id);
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
