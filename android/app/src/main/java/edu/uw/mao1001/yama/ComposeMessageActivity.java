package edu.uw.mao1001.yama;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Created by Nick on 5/1/2016.
 */
public class ComposeMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_message);

        setTitle(R.string.title_compose_message);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button button = (Button)findViewById(R.id.button_send_message);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendMessage();
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

    private void sendMessage() {
        returnHome();
    }

    private void returnHome() {
        NavUtils.navigateUpFromSameTask(this);
    }
}
