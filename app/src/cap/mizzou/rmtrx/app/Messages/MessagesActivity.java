package cap.mizzou.rmtrx.app.Messages;

import Models.ChatLog;
import Models.Message;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import cap.mizzou.rmtrx.app.DataAccess.Resident;
import cap.mizzou.rmtrx.app.R;
import cap.mizzou.rmtrx.app.User_setup.UserInfo;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 9/16/13
 * Time: 10:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class MessagesActivity extends ListActivity {
    private List<Message> messageList;
    private RestAdapter restAdapter;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_list);
        getActionBar().setTitle("Messages");
        getActionBar().setDisplayHomeAsUpEnabled(true);
        userInfo=new UserInfo(this);
        restAdapter=new RestAdapter.Builder().setServer("http://powerful-thicket-5732.herokuapp.com/").build();
//        displayMessageList();
        getMessagesFromServer();

    }

    private List<Message> getAllMessages() {

        return null;  //To change body of created methods use File | Settings | File Templates.
    }
    
    public void getMessagesFromServer() {
        MessagesDataInterface ri=restAdapter.create(MessagesDataInterface.class);
        
        ri.getChatLog(userInfo.getResidenceId(),new Callback<List<Message>>() {
            @Override
            public void success(List<Message> messages, Response response) {
               displayMessageList(messages);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d("Messages","Could not receive from server");
            }
        });
    }

    private void displayMessageList(List<Message> listOfMessages) {
        List<Message> messages=listOfMessages;
//        Message message=new Message();
//        message.setMessage("Hey Mike");
//        message.setDateSent(new Date());
//        message.setId("12345");
//        message.setSenderId("me");
//        messages.add(message);

        ArrayAdapter<Message> adapter=new ArrayAdapter<Message>(this,android.R.layout.simple_list_item_1,messages);
        setListAdapter(adapter);
    }
    public void sendMessage(View view) {
              if(grabMessage()!=null && !grabMessage().isEmpty())
                sendMessageToServer(grabMessage());
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        getMessagesFromServer();
    }

    private String grabMessage() {
        EditText message=(EditText)findViewById(R.id.message);
        return message.getText().toString();
    }


    public void sendMessageToServer(String message) {
        MessagesDataInterface ri=restAdapter.create(MessagesDataInterface.class);

        ri.sendMessage(userInfo.getId(),userInfo.getResidenceId(), message, new Callback<Message>() {
            @Override
            public void success(Message message, Response response) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }


}
