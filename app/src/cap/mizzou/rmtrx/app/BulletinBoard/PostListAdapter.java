package cap.mizzou.rmtrx.app.BulletinBoard;

import android.widget.ArrayAdapter;
import android.content.Context;
import java.util.*;
import android.view.*;
import android.widget.*;
import cap.mizzou.rmtrx.app.R;
import cap.mizzou.rmtrx.app.User_setup.UserInfo;
import android.app.Activity;



/**
 * Created with IntelliJ IDEA.
 * User: Desi
 * Date: 11/14/13
 * Time: 2:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class PostListAdapter extends BaseAdapter {

    private List<Post> items;
    private int layoutResourceId;
    private Context context;
    private UserInfo userinfo;

    private final List<Post> posts;

    public PostListAdapter(final Context context, final int itemResId,
                           final List<Post> items) {
        this.posts = items;
    }

    public int getCount() {
        return this.posts.size();
    }

    public Object getItem(int position) {
        return this.posts.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    /**
     * Set the content for a row here
     */
    public View getView(int position, View convertView, ViewGroup parent) {

        final Post post = this.posts.get(position);
        View itemView = null;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.post_list_item, null);
        } else {
            itemView = convertView;
        }

        // Set the text of the row
        TextView txtId = (TextView) itemView.findViewById(R.id.post_title);
        txtId.setText(post.getPostTitle());



        // Remember the row for each button so that we can refer to
        // it when the button is clicked
        ImageButton imgButton = (ImageButton) itemView.findViewById(R.id.delete_button);
        imgButton.setTag(post);

        return itemView;

    }


    /**
     * Delete a row from the list of rows

     */
    public void removePost(Post post) {

        if(this.posts.contains(post)) {
            this.posts.remove(post);
        }
    }
}



