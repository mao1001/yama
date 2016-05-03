package edu.uw.mao1001.yama;

import android.provider.Telephony.Sms.Inbox;
import android.app.Fragment;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.app.LoaderManager;
import android.content.Loader;
import android.content.CursorLoader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Nick on 5/1/2016.
 */
public class MessageListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "FRAGMENT - MessageList";

    private SimpleCursorAdapter adapter;



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

        initializeAdapter(rootView);
        return rootView;
    }


    //-------------------------------------//
    //   L O A D E R   O V E R R I D E S   //
    //-------------------------------------//

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection =
                {
                        Inbox._ID,
                        Inbox.PERSON,
                        Inbox.ADDRESS,
                        Inbox.BODY,
                        Inbox.DATE_SENT
                };

        //Creates loader for query
        return new CursorLoader(
                getActivity(),
                Inbox.CONTENT_URI,
                projection,
                null,
                null,
                Inbox.DEFAULT_SORT_ORDER
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            adapter.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    //-----------------------------------//
    //   P R I V A T E   M E T H O D S   //
    //-----------------------------------//

    /**
     * Starts an adapter to hook up to the loader manager.
     * @param rootView
     */
    private void initializeAdapter(View rootView) {
        adapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.list_item,
                null,
                new String[] {Inbox.ADDRESS, Inbox.BODY},
                new int[] {R.id.label_contact_name, R.id.label_message_body},
                0
        );

        AdapterView listView = (AdapterView)rootView.findViewById(R.id.message_list);
        listView.setAdapter(adapter);

        getLoaderManager().initLoader(0, null, this);
    }
}
