package edu.uw.mao1001.yama;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Nick on 5/1/2016.
 */
public class MessageListFragment extends Fragment {
    private static final String TAG = "FRAGMENT - MessageList";

    //-----------------------------//
    //   C O N S T R U C T O R S   //
    //-----------------------------//

    //Required blank constructor
    public MessageListFragment() {}

    /**
     *
     * @return
     */
    public static MessageListFragment newInstance() {
        return new MessageListFragment();
    }

    //-----------------------------------------//
    //   F R A G M E N T   O V E R R I D E S   //
    //-----------------------------------------//

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_message_list, container, false);

        return rootView;
    }
}
