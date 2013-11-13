package cap.mizzou.rmtrx.core.ui;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import cap.mizzou.rmtrx.core.ObjectGraphProvider;
import com.squareup.otto.Bus;

import javax.inject.Inject;

/**
 * A {@link ListFragment} that can be injected and is automatically registered for events on the injected {@link Bus}.
 */
public class BaseListFragment extends ListFragment {

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
