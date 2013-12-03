package cap.mizzou.rmtrx.app.Messages;

import Models.Message;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import cap.mizzou.rmtrx.app.R;
import cap.mizzou.rmtrx.app.User_setup.UserInfo;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 9/16/13
 * Time: 10:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class MessagesActivity extends Activity {
    private List<Message> messageList;
    private RestAdapter restAdapter;
    private UserInfo userInfo;
    private EditText messageEditText;
    private MessagesArrayAdapter adapter;
    private Handler handler;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discuss);
        userInfo=new UserInfo(this);

        ListView lv = (ListView) findViewById(R.id.listView1);
        adapter = new MessagesArrayAdapter(getApplicationContext(), R.layout.listitem_discuss, userInfo.getId());
        lv.setAdapter(adapter);


        getActionBar().setTitle("Messages");
        messageEditText =(EditText)findViewById(R.id.messageText);
        restAdapter=new RestAdapter.Builder().setServer("http://powerful-thicket-5732.herokuapp.com/").build();
        getMessagesFromServer();

//        DatabaseHydrator dh = new DatabaseHydrator(this);
//        String residenceId = userInfo.getResidenceId();
//        dh.UpdateDatabase(residenceId);

        handler = new Handler();
        timer = new Timer();

        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @SuppressWarnings("unchecked")
                    public void run() {
                        try {
                            Log.d("Timer:", "Tick");
                            getMessagesFromServer();
                        }
                        catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 10000, 5000);

    }

    protected void onPause() {
         super.onPause();
         timer.cancel();
    }

    protected void onResume() {
        super.onResume();

        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @SuppressWarnings("unchecked")
                    public void run() {
                        try {
                            Log.d("Timer:", "Tick");
                            getMessagesFromServer();
                        }
                        catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };

        timer.schedule(doAsynchronousTask, 10000, 5000);
    }
    
    public void getMessagesFromServer() {
        MessagesDataInterface ri=restAdapter.create(MessagesDataInterface.class);
        
        ri.getChatLog(userInfo.getResidenceId(),new Callback<List<Message>>() {
            @Override
            public void success(List<Message> messages, Response response) {
                adapter.clearMessagesCollection();
                adapter.notifyDataSetChanged();
                for(Message message : messages) {
                    adapter.add(message);
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d("Messages","Could not receive from server");
            }
        });
    }

    public void sendMessage(View view) {
              if(grabMessage()!=null && !grabMessage().isEmpty())
                sendMessageToServer(grabMessage());
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        clearMessageBox();
//        getMessagesFromServer();
    }

    private void clearMessageBox() {
          messageEditText.setText("");
    }

    private String grabMessage() {
        return messageEditText.getText().toString();
    }


    public void sendMessageToServer(String message) {
        MessagesDataInterface ri=restAdapter.create(MessagesDataInterface.class);

        ri.sendMessage(userInfo.getId(),userInfo.getResidenceId(), message, new Callback<Message>() {
            @Override
            public void success(Message message, Response response) {
                adapter.add(message);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }


}
