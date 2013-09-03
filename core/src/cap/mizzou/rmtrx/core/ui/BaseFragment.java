package cap.mizzou.rmtrx.core.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import cap.mizzou.rmtrx.core.ObjectGraphProvider;
import com.squareup.otto.Bus;

import javax.inject.Inject;

/**
 * @author Steven Rodenberg
 */
public class BaseFragment extends Fragment {

    @Inject
    Bus bus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((ObjectGraphProvider) getActivity().getApplication()).objectGraph().inject(this);
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
