package edu.uw.mao1001.yama;

import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Telephony;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    //-----------------------//
    //   O V E R R I D E S   //
    //-----------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab);
        if (myFab != null) {
            myFab.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    launchComposeMessageFragment();
                }
            });
        }

        IntentFilter messageFilter = new IntentFilter();
        messageFilter.addAction(ComposeMessageActivity.ACTION_SMS_STATUS);
        messageFilter.addAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        this.registerReceiver(new MyReceiver(), messageFilter);
    }

    private void launchComposeMessageFragment() {
        Intent intent = new Intent(this, ComposeMessageActivity.class);
        startActivity(intent);
    }
}
