package edu.uw.mao1001.yama;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

        Fragment fragment = MessageListFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment, "MessageListFragment");
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.toString()) {
            case "Settings":
                launchSettings();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    //-----------------------------------//
    //   P R I V A T E   M E T H O D S   //
    //-----------------------------------//

    /**
     * Launches the settings activity
     */
    private void launchSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    /**
     * Launches the compose message activity
     */
    private void launchComposeMessageFragment() {
        Intent intent = new Intent(this, ComposeMessageActivity.class);
        startActivity(intent);
    }
}
