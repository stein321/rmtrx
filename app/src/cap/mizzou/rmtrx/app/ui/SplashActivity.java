package cap.mizzou.rmtrx.app.ui;

/**
 * Created with IntelliJ IDEA.
 * User: AlecTegels
 * Date: 11/7/13
 * Time: 5:52 PM
 * To change this template use File | Settings | File Templates.
 */

import java.util.Timer;
import java.util.TimerTask;
import cap.mizzou.rmtrx.app.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {
    private long splashTime = 5000; //5 seconds


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        TimerTask task = new TimerTask()
        {
            @Override
            public void run() {
                finish();
                Intent mainIntent = new Intent().setClass(SplashActivity.this, HomeActivity.class);
                startActivity(mainIntent);
            }

        };

        Timer timer = new Timer();
        timer.schedule(task, splashTime);
    }
}