package cap.mizzou.rmtrx.app.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cap.mizzou.rmtrx.app.R;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 9/29/13
 * Time: 10:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class RegistrationFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.registration_page, container, false);
    }
}
