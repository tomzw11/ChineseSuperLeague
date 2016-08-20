package tom.chinesesuperleague;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;

import tom.chinesesuperleague.sync.CSLSyncAdapter;

public class MainActivity extends AppCompatActivity {

    private String preferPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        preferPlayer = Utility.getPreferredPlayer(this);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPager.setOffscreenPageLimit(2);//Might not be necessary?

        PagerAdapter newPagerAdapter = new PagerAdapter(getSupportFragmentManager(),
                MainActivity.this);

        viewPager.setAdapter(newPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //TODO:Switch tab text to pictures.


        CSLSyncAdapter.initializeSyncAdapter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String newPreferredPlayer = Utility.getPreferredPlayer( this );
        // update the location in our second pane using the fragment manager
        if ( preferPlayer != null && !preferPlayer.equals(newPreferredPlayer)) {

            //ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

            MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentByTag
                    ("android:switcher:"+R.id.viewpager+":"+"0");

            FetchFragment fetchFragment = (FetchFragment) getSupportFragmentManager().findFragmentByTag
                    ("android:switcher:"+R.id.viewpager+":"+"1");

                if (mainFragment != null && fetchFragment != null){

                    mainFragment.onPlayerChanged();
                    fetchFragment.onPlayerChanged();
                    preferPlayer = newPreferredPlayer;
                }
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

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_menu_settings) {

            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
