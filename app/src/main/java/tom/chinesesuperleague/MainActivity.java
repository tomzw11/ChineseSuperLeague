package tom.chinesesuperleague;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.ActivityOptionsCompat;


import tom.chinesesuperleague.sync.CSLSyncAdapter;

public class MainActivity extends AppCompatActivity {


    String preferPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferPlayer = Utility.getPreferredPlayer(this);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();
        }

        CSLSyncAdapter.initializeSyncAdapter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String newPreferredPlayer = Utility.getPreferredPlayer( this );
        // update the location in our second pane using the fragment manager
        if ( preferPlayer != null && !preferPlayer.equals(newPreferredPlayer)) {
            MainFragment ff = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.container);
            if ( null != ff ) {
                ff.onPlayerChanged();
            }

            preferPlayer = newPreferredPlayer;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }
}
