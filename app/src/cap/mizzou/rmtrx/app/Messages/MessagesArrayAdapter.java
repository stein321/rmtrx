package cap.mizzou.rmtrx.app.Messages;

import Models.Message;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import cap.mizzou.rmtrx.app.DataAccess.Resident;
import cap.mizzou.rmtrx.app.DataAccess.ResidentDataSource;
import cap.mizzou.rmtrx.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mam8cc
 * Date: 11/29/13
 * Time: 1:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class MessagesArrayAdapter extends ArrayAdapter<Message> {

    private TextView message;
    private List<Message> messages = new ArrayList<Message>();
    private LinearLayout wrapper;
    private String currentUserId;

    public MessagesArrayAdapter(Context context, int resource, String currentUserId) {
        super(context, resource);
        this.currentUserId = currentUserId;
    }

    @Override
    public void add(Message object) {
        messages.add(object);
        super.add(object);
    }

    public int getCount() {
        return this.messages.size();
    }

    public Message getItem(int index) {
        return this.messages.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.listitem_discuss, parent, false);
        }

        wrapper = (LinearLayout) row.findViewById(R.id.wrapper);

        Message comment = getItem(position);

        message = (TextView) row.findViewById(R.id.comment);

        if(comment.isOwnMessage(currentUserId)) {
            message.setText(comment.getMessage());
        }
        else {
            ResidentDataSource dataSource = new ResidentDataSource(getContext());
            dataSource.open();
            Resident user = dataSource.findResident(comment.getSenderId());
            dataSource.close();
            message.setText(user.getFirstName() + ": " + comment.getMessage());
        }

        message.setBackgroundResource(comment.isOwnMessage(currentUserId) ? R.drawable.bubble_green : R.drawable.bubble_yellow);
        wrapper.setGravity(comment.isOwnMessage(currentUserId) ? Gravity.RIGHT : Gravity.LEFT);

        return row;
    }

    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public void clearMessagesCollection() {
        messages.clear();
    }
}
