package cap.mizzou.rmtrx.app.Messages;

import Models.ChatLog;
import Models.Message;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
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
        setContentView(R.layout.resident_list);
        getActionBar().setTitle("Messages");
        getActionBar().setDisplayHomeAsUpEnabled(true);
        userInfo=new UserInfo(this);
        restAdapter=new RestAdapter.Builder().setServer("http://powerful-thicket-5732.herokuapp.com/").build();
        displayMessageList();
        getMessagesFromServer();

    }

    private List<Message> getAllMessages() {

        return null;  //To change body of created methods use File | Settings | File Templates.
    }
    
    public void getMessagesFromServer() {
        MessagesDataInterface ri=restAdapter.create(MessagesDataInterface.class);
        
        ri.getChatLog(userInfo.getResidenceId(),new Callback<ChatLog>() {
            @Override
            public void success(ChatLog chatLog, Response response) {
//               displayMessageList(chatLog);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d("Messages","Could not receive from server");
            }
        });
    }

    private void displayMessageList() {
        List<Message> messages=new ArrayList<Message>();
        Message message=new Message();
        message.setMessage("Hey Mike");
        message.setDateSent(new Date());
        message.setId("12345");
        message.setSenderId("me");
        messages.add(message);

        ArrayAdapter<Message> adapter=new ArrayAdapter<Message>(this,android.R.layout.simple_list_item_1,messages);
        setListAdapter(adapter);
    }


}
