package edu.uw.mao1001.yama;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Nick on 5/2/2016.
 */
public class MyReceiver extends BroadcastReceiver {

    public static final String TAG = "RECEIVER";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v(TAG, "Received something" + intent.toString());
        if (intent.getAction() == ComposeMessageActivity.ACTION_SMS_STATUS) {
            if (getResultCode() == Activity.RESULT_OK) {
                Toast.makeText(context, "Message successfully sent", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Error sending message", Toast.LENGTH_SHORT).show();
            }
        } else if (intent.getAction() == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            Toast.makeText(context, "Message received!", Toast.LENGTH_SHORT).show();
        }
    }
}
