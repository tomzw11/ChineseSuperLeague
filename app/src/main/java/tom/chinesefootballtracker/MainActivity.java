package tom.chinesefootballtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.SearchView;

import io.fabric.sdk.android.services.network.HttpRequest;
import tom.chinesefootballtracker.sync.CSLSyncAdapter;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private String preferPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        preferPlayer = Roster.getPreferredPlayer(this);

        //TODO:Use a special toolbar app name font.
        //TODO:Use Proguard to minimize unused code.
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPager.setOffscreenPageLimit(3);//Might not be necessary?

        PagerAdapter newPagerAdapter = new PagerAdapter(getSupportFragmentManager(),
                MainActivity.this);

        viewPager.setAdapter(newPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        CSLSyncAdapter.initializeSyncAdapter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String newPreferredPlayer = Roster.getPreferredPlayer( this );
        Intent intent_scout = getIntent();
        String newp = intent_scout.getStringExtra("newPreference");

        if(newp!=null){

            Roster.setPreferredPlayer(this,newp);

            MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentByTag
                    ("android:switcher:"+R.id.viewpager+":"+"1");

            FetchFragment fetchFragment = (FetchFragment) getSupportFragmentManager().findFragmentByTag
                    ("android:switcher:"+R.id.viewpager+":"+"2");

            if (mainFragment != null && fetchFragment != null){
                mainFragment.onPlayerChanged();
                fetchFragment.onPlayerChanged();
                preferPlayer = newp;
                newPreferredPlayer = newp;
            }
        }

        // update the location in our second pane using the fragment manager
        if ( preferPlayer != null && !preferPlayer.equals(newPreferredPlayer)) {

            MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentByTag
                    ("android:switcher:"+R.id.viewpager+":"+"1");

            FetchFragment fetchFragment = (FetchFragment) getSupportFragmentManager().findFragmentByTag
                    ("android:switcher:"+R.id.viewpager+":"+"2");

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
    public boolean onQueryTextSubmit(String query) {
        // User pressed the search button
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // User changed the text
        return false;
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

//    //For action button.
//    public void displayGraph(View view) {
//
////        Intent intent = new Intent(this, SearchActivity.class);
////
////        ActivityOptionsCompat activityOptions =
////                ActivityOptionsCompat.makeSceneTransitionAnimation(this);
////        ActivityCompat.startActivity(this, intent, activityOptions.toBundle());
//
//            FragmentManager fm = getSupportFragmentManager();
//            FormFragment formFragment = FormFragment.newInstance();
//            formFragment.show(fm, "fragment_alert");
//    }
}
