package cap.mizzou.rmtrx.app.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 9/25/13
 * Time: 8:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class sendLoginInfo extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://powerful-thicket-5732.herokuapp.com/user");
        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("username", "ben2"));
            nameValuePairs.add(new BasicNameValuePair("password", "test"));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }

}