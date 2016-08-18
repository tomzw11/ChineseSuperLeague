package tom.chinesesuperleague;

import android.app.Fragment;
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

    String preferPlayer;

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

        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(),
                MainActivity.this));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        CSLSyncAdapter.initializeSyncAdapter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String newPreferredPlayer = Utility.getPreferredPlayer( this );
        // update the location in our second pane using the fragment manager
        if ( preferPlayer != null && !preferPlayer.equals(newPreferredPlayer)) {

            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

            MainFragment ff = (MainFragment) getSupportFragmentManager().findFragmentByTag
                    ("android:switcher:"+R.id.viewpager+":"+viewPager.getCurrentItem());

            if ( null != ff ) {
                ff.onPlayerChanged();
            }else if(null ==ff)System.out.println("ff is null");
            //System.out.println(preferPlayer+" has changed to  "+ newPreferredPlayer);
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


        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_menu_settings) {

            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
