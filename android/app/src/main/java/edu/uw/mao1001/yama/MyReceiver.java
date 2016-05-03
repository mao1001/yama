package edu.uw.mao1001.yama;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Telephony;
import android.telephony.SmsMessage;
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
        if (intent.getAction().equals(SMSSendService.ACTION_SMS_STATUS)) {
            if (getResultCode() == Activity.RESULT_OK) {
                Toast.makeText(context, "Message successfully sent", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Error sending message", Toast.LENGTH_SHORT).show();
            }
        } else if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            Toast.makeText(context, "Received message!", Toast.LENGTH_LONG).show();

            if (prefs.getBoolean(SettingsFragment.KEY_PREF_AUTO_REPLY, false)) {
                Bundle extras = intent.getExtras();
                SmsMessage[] msgs = null;
                String msg_from;
                if (extras != null){
                    //---retrieve the SMS message received---
                    try{
                        Object[] pdus = (Object[]) extras.get("pdus");
                        msgs = new SmsMessage[pdus.length];
                        msgs[0] = SmsMessage.createFromPdu((byte[])pdus[0]);
                        String number = msgs[0].getOriginatingAddress();
                        String message = prefs.getString(SettingsFragment.KEY_PREF_PRESET_MESSAGE, "");

                        autoReply(context, number, message);

                    } catch(Exception e){
                            Log.d("Exception caught",e.getMessage());
                    }
                } else {
                    Toast.makeText(context, "Cannot auto-reply", Toast.LENGTH_LONG).show();

                }
            }
        }
    }

    public void autoReply(Context context, String number, String message) {
        Intent smsIntent = new Intent(context, SMSSendService.class);
        smsIntent.setAction(SMSSendService.ACTION_SMS_STATUS);
        Bundle extras = new Bundle();
        extras.putString("number", number);
        extras.putString("message", message);
        smsIntent.putExtras(extras);
        context.startService(smsIntent);
        Toast.makeText(context, "Auto-replying message", Toast.LENGTH_SHORT).show();
    }
}
