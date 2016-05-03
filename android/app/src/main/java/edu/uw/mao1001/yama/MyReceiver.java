package edu.uw.mao1001.yama;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Telephony;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Nick on 5/2/2016.
 */
public class MyReceiver extends BroadcastReceiver {

    public static final String TAG = "RECEIVER";

    //-----------------------//
    //   O V E R R I D E S   //
    //-----------------------//

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
            Toast.makeText(context, "Received message!", Toast.LENGTH_LONG).show();

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            String number = "";
            String body = "";
            Bundle extras = intent.getExtras();

            try{
                Object[] pdus = (Object[]) extras.get("pdus");
                SmsMessage[] msgs = new SmsMessage[pdus.length];
                msgs[0] = SmsMessage.createFromPdu((byte[])pdus[0]);
                number = msgs[0].getOriginatingAddress();
                body = msgs[0].getMessageBody();

                //Sends a notification with the number and the body of the message to the system.
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_message_white_24dp)
                        .setContentTitle(number)
                        .setContentText(body);


                NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(0, mBuilder.build());


            } catch(Exception e){
                Log.d("Exception caught",e.getMessage());
            }

            //Checks to make sure the user has auto-reply on and if a number was successfully extracted.
            if (prefs.getBoolean(SettingsFragment.KEY_PREF_AUTO_REPLY, false) && !number.equals("")) {
                if (extras != null){
                    //Sends an auto-reply
                    String message = prefs.getString(SettingsFragment.KEY_PREF_PRESET_MESSAGE, "");
                    autoReply(context, number, message);
                } else {
                    Toast.makeText(context, "Cannot auto-reply", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    //-----------------------------------//
    //   P R I V A T E   M E T H O D S   //
    //-----------------------------------//

    /**
     * Sends an auto-reply back to the passed in number.
     * @param context : context
     * @param number : Receiving number
     * @param message : Message to be sent out.
     */
    private void autoReply(Context context, String number, String message) {
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
