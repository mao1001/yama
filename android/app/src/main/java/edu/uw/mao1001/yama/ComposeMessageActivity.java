package edu.uw.mao1001.yama;

import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Nick on 5/1/2016.
 */
public class ComposeMessageActivity extends AppCompatActivity {

    public static final String TAG = "ComposeMessageActivity";

    public static final int PICK_CONTACT = 1;
    private static final int SMS_SENT_CODE = 2;

    public static final String ACTION_SMS_STATUS = "edu.uw.mao1001.yama.ACTION_SMS_STATUS";
    //-----------------------//
    //   O V E R R I D E S   //
    //-----------------------//

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_message);

        setTitle(R.string.title_compose_message);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button sendButton = (Button)findViewById(R.id.button_send_message);
        if (sendButton != null) {
            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendMessage();
                }
            });
        }

        Button selectButton = (Button)findViewById(R.id.button_select_contact);
        if (selectButton != null) {
            selectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getContact();
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                returnHome();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_CONTACT) {
                getNumber(data);
            }
        }
    }

    //-----------------------------------//
    //   P R I V A T E   M E T H O D S   //
    //-----------------------------------//

    /**
     * Private helper used to get the number of a user selected.
     * @param data: Intent that contains the URI of a contact.
     */
    private void getNumber(Intent data) {
        Cursor cursor = getContentResolver().query(data.getData(), null, null, null, null);
        cursor.moveToFirst();
        String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
        String hasNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

        if (hasNumber.equals("1")) {
            Cursor cursorNum = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);

            cursorNum.moveToFirst();
            String number = cursorNum.getString(cursorNum.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            EditText field = (EditText)findViewById(R.id.field_select_contact);
            field.setText(number);
        }
    }

    /**
     * Sends a text message out. If there is invalid input, this will notify the user.
     */
    private void sendMessage() {
        EditText number = (EditText)findViewById(R.id.field_select_contact);
        EditText message = (EditText)findViewById(R.id.field_compose_message);
        if (!number.getText().toString().equals("") || !message.getText().toString().equals("")) {
            Toast.makeText(this, "Sending Message", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(ACTION_SMS_STATUS);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, SMS_SENT_CODE, intent, 0);

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number.getText().toString(), null, message.getText().toString(), pendingIntent, null);
            returnHome();
        } else {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method that simply navigates the user away from this activity to the one directly before.
     * This is essentially an up button press.
     */
    private void returnHome() {
        NavUtils.navigateUpFromSameTask(this);
    }

    /**
     * Starts and activity that can pick a contact and return it to us.
     */
    private void getContact() {
        Intent getContactIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        getContactIntent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        startActivityForResult(getContactIntent, PICK_CONTACT);
    }
}
