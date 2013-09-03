package cap.mizzou.rmtrx.core.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import cap.mizzou.rmtrx.core.ObjectGraphProvider;
import com.squareup.otto.Bus;

import javax.inject.Inject;

/**
 * @author Steven Rodenberg
 */
public class BaseFragmentActivity extends FragmentActivity {

    @Inject
    Bus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((ObjectGraphProvider) getApplication()).objectGraph().inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        bus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        bus.unregister(this);
    }
}
